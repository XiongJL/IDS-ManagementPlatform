package com.neu.rule.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.rule.beans.IdsRule;
import com.neu.rule.dao.RuleDao;

@Service
public class DbOutServiceImpl implements DbOutService {

	@Autowired
	private RuleDao ruledao;

	public class StreamGobbler extends Thread {

		InputStream is;
		String type;

		public StreamGobbler(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					if (type.equals("Error")) {
						//System.out.println("Error   :" + line);
					} else {
						//System.out.println("Debug:" + line);
					}
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@Override
	public void shell(String cmd) {
		String[] cmds = { "/bin/sh", "-c", cmd };
		Process process;

		try {
			process = Runtime.getRuntime().exec(cmds);

			StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "Error");
			StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "Output");
			errorGobbler.start();
			outputGobbler.start();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insertFile(String url) {
		File inFile = new File(url);
		// 临时文件
		try {
			File outFile = File.createTempFile("name", ".tmp");
			// 输入
			FileInputStream fis = new FileInputStream(inFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			// 输出
			FileOutputStream fos = new FileOutputStream(outFile);
			PrintWriter out = new PrintWriter(fos);
			// 保存一行数据
			String thisLine;
			// 行号从1开始
			int i = 1;
			
			while ((thisLine = in.readLine()) != null) {
				// 如果行号等于目标行，则输出要插入的数据
				if (thisLine.indexOf("DROP TABLE IF EXISTS") != -1) {
					out.println("SET FOREIGN_KEY_CHECKS=0;");
					out.println("set character_set_client = utf8;");
					out.println("set character_set_connection = utf8;");
				}
				// 输出读取到的数据
				if(!thisLine.startsWith("/")){
					out.println(thisLine);
				}
				
				// 行号增加
				i++;
			}
			out.flush();
			out.close();

			in.close();

			// 删除原始文件
			inFile.delete();
			// 把临时文件改名为原文件名
			outFile.renameTo(inFile);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	// 增加一个我们有多少已发布漏洞，多少条规则的总数。
	
	
	public String ids_updatePath(String[] nvids) {   //返回打包完成后的压缩包路径
		// 循环数组长度,定义 where 条件
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
		String date = df.format(new Date());
		String where = "";
		for (int i = 0; i < nvids.length; i++) {
			where += "nvid=" + nvids[i] + "  or ";
		}
		where = where.substring(0, where.length() - 3);
		shell("/usr/bin/mysqldump -urule -pneteye --skip-extended-insert ids_rule ids_rule --"
				+ "where='" + where + "'>" + " /www/wwwroot/rule/pack/ids_update_" + date + ".sql");
		String sqlpath = "/www/wwwroot/rule/pack/ids_update_" + date + ".sql";
		insertFile(sqlpath); //写入 set character
		Map<String,List<String>> id2pacp = new HashMap<>();
		IdsRule rule = null;
		for (String n : nvids) {
			List<String> pcaps = new ArrayList<>();
			int id = Integer.valueOf(n);
			rule = ruledao.findByNvid(id);// 将打包过的ids_flag置为 1
			if (rule.getPcap_path1() != null && rule.getPcap_path1() != "")
				pcaps.add(rule.getPcap_path1());
			if (rule.getPcap_path2() != null && rule.getPcap_path2() != "")
				pcaps.add(rule.getPcap_path2());
			if (rule.getPcap_path3() != null && rule.getPcap_path3() != "")
				pcaps.add(rule.getPcap_path3());
			if (rule.getPcap_path4() != null && rule.getPcap_path4() != "")
				pcaps.add(rule.getPcap_path4());
			if (rule.getPcap_path5() != null && rule.getPcap_path5() != "")
				pcaps.add(rule.getPcap_path5());
			if (rule.getOther_path() != null && rule.getOther_path() != "")
				pcaps.add(rule.getOther_path());
			if(!pcaps.isEmpty())   //list不为空才put到map中
				id2pacp.put(n, pcaps);  
		}
		// 创建一个文件夹,在文件夹下再创建一个pcap文件夹将所有pcap复制进来,再将sql移动过来,压缩整个文件夹.
		String dir = "./pack/ids_update_"+date;
		String pcapDir = dir+"/pcap";
		shell("mkdir -p "+dir);   //创建主文件夹,代压缩
		shell("mv -f "+sqlpath+" "+dir); //移动sql文件到 dir 文件夹中
		shell("mkdir -p "+pcapDir); //创建pcap包文件夹
		/*迭代Map,根据nvid创建目录,并将对应得pcap复制到nvid的目录下*/
		for(Map.Entry<String,List<String>> entry : id2pacp.entrySet()){		
			shell("mkdir -p "+pcapDir+"/"+entry.getKey());  //创建nvid文件夹
			for(String pcapurl : entry.getValue()){
				shell("cp -f "+pcapurl+" "+pcapDir+"/"+entry.getKey()); //复制
			}
		}
		//调用updateTxtPath 方法,获得文本文件路径,并且移动到ids_updat_date目录下
		String txtPath=null;
		try {
			txtPath = updateTxtPath(nvids,date);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shell("mv -f "+txtPath+" "+dir); //移动
		//打包压缩成一个压缩包   ids_update_date
		shell("tar -zcf "+dir+".tgz"+" -C"+"./pack/ ids_update_"+date);//压缩 ,注意空格
		/*删除文件夹*/
		shell("rm -rf "+dir);
		//
		
		return "/www/wwwroot/rule/pack/ids_update_"+date+".tgz";
	}

	public String updateTxtPath(String[] nvids,String date) throws IOException, ParseException {
		List<String> pcaps = new ArrayList<>();
		IdsRule rule = null;
		for (String n : nvids) {
			int id = Integer.valueOf(n);
			rule = ruledao.findByNvid(id);// 将打包过的ids_flag置为 1
			rule.setIds_flag(1);
			ruledao.save(rule);
		}

		// 判断text是否存在
		// packLog, 记录上次打包的时间		
		File packLog = new File("/www/wwwroot/rule/pack/packLog.txt");
		if (!packLog.exists()) {
			try {
				packLog.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 倒叙读取文件最后一行 ,以及在打包完成后记录本次打包的时间
		RandomAccessFile raf = new RandomAccessFile(packLog, "rw");
		long len = raf.length();
		String lastDate = "";
		boolean isNeedCompare = false;
		if (len != 0L) {
			// 有date
			isNeedCompare = true;
			long pos = len - 1;// 定位文件尾
			while (pos > 0) { // 判断文件是否到达头
				--pos; // 一个字符一个字符的向前移动指针
				raf.seek(pos); // 定位文件指针所指的位置
				if (raf.readByte() == '\n') { // 如果是换行符,就可以读取该行了
					lastDate = raf.readLine();
					break;
				}
			}
		}

		// 打包文件名
		String ids_name = "ids_update_" + date + ".txt";
		// String ids_last_name = "ids_update"+lastDate+".txt"; //上次打包的文件
		String path = "/www/wwwroot/rule/pack/";
		File ids_update = new File(path + ids_name);
		if (ids_update.exists()) { // 文件存在,则删除. 前台限制,一分钟内不能提交两次
			ids_update.delete();
		} else {
			ids_update.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(ids_update);
		PrintWriter out = new PrintWriter(fos);
		if (isNeedCompare) { // 需要比较,上次打过包.
			// 获取上次打包到这次打包时间内的ids_flag为0的漏洞
			SimpleDateFormat a = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
			Date publish1 = a.parse(lastDate);
			long[] InvalidNvids = ruledao.findNvidByPublish_date(publish1, new Date());
			// 写入ids_update_xxxx.txt 文件
			String line = "";
			String id;
			for (long nid : InvalidNvids) {
				id = String.valueOf(nid);
				line += id + ",";
			}
			out.println("本次打包将以下漏洞置为失效:\r\n");
			if (line.length() == 0) {
				out.println("无 \r\n");
			} else {
				line = line.substring(0, line.length() - 1);
				out.println(line);
			}
			out.flush();
		} else {
			out.println("上次打包到现在失效的有:\r\n");
			out.println("无 \r\n");
		}
		String line = "";
		for (String nid : nvids) {
			line += nid + ",";
		}

		out.println("\r\n本次打包的漏洞有:\r\n");
		if (line.length() > 0) {
			line = line.substring(0, line.length() - 1);
			out.println(line);
		} else {
			out.println("无 \r\n");
		}
		
		/* 
		 * 已发布漏洞的总数。
		 * */
		long[] countByIdsFlag = ruledao.countByIdsFlag();
		out.printf("\r\n已发布的漏洞有:\r\n");
		if(countByIdsFlag.length>0){
			out.print(countByIdsFlag.length);
		}else{
			out.print("无");
		}
		
		/*
		 * 已发布的规则总数
		*/
		List<IdsRule> countByRules = ruledao.countByRules();
		long count = 0;
		for (IdsRule idsRule : countByRules) {
			if(idsRule.getRule1().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule2().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule3().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule4().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule5().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule6().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule7().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule8().equals(null)){
				break;
			}else{
				count++;
			}
			if(idsRule.getRule9().equals(null)){
				break;
			}else{
				count++;
			}
		}
		out.printf("\r\n已发布的规则有:\r\n");
		if(count>0){
			out.print(count);
		}else{
			out.print("无");
		}
		
		out.flush();
		fos.close();
		// packLog写入打包date
		raf.seek(len);
		raf.write("\n".getBytes());
		raf.write(date.getBytes());
//		raf.write("\n".getBytes());
		raf.close();
		return path + ids_name;
	}

	// 添加一个版本文件
	@Override
	public void mkver(String path, String content) {
		File file = new File(path + "/system-event.ver");
		try {
			if (file.exists()) { // 文件存在,则删除. 前台限制,一分钟内不能提交两次
				file.delete();
			} else {
				file.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter out = new PrintWriter(fos);
			out.println(content);
			out.flush();
			out.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//			out.close();
//			fos.close();
		}
	}
}
