package com.neu.rule.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neu.rule.beans.IdsRule;
import com.neu.rule.dao.RuleDao;
import com.neu.rule.service.DbOutService;

@RestController
@CrossOrigin
public class DbOutController {
	@Autowired
	private RuleDao ruledao;
	@Autowired
	private DbOutService doservice;

	/**
	 * 全量包下载
	 */
	@GetMapping(value = "/rule/pack/all")
	public ResponseEntity<InputStreamResource> packAll(HttpServletRequest req) throws Exception {
		/**
		 * 思路: 1 : 获取POST请求, 获取选择的nvid,根据NVID查询对应漏洞 2: 导出SQL ,并根据nvid打包对应的pcap包
		 * 3 : 导出全量SQL ,不需要pcap包
		 */
		// 全量包 ids_ 日期
		// 导出sql
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
		String date = df.format(new Date());
		SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd");
		String dateVer = d.format(new Date());
		//导出sql
		doservice
				.shell("/usr/bin/mysqldump -urule -pneteye --skip-extended-insert ids_rule ids_rule --where='ids_flag=1'> /www/wwwroot/rule/ids_rule_"
						+ date + ".sql");

		// 在SQL 中添加
		// set character_set_client = utf8; 换行符 \n
		// set character_set_connection = utf8;
		String url = "/www/wwwroot/rule/ids_rule_" + date + ".sql";
		doservice.insertFile(url);
		String dir = "/www/wwwroot/rule/";
		doservice.mkver(dir,"03"+dateVer); //创建版本文件
		// 压缩tgz
		doservice.shell("tar -zcf /www/wwwroot/rule/pack/ids_rule_" + date + ".tgz" +" ids_rule_" + date + ".sql "+"system-event.ver");//-C 后面后空格,打包忽略前缀目录
		// 加密后,文件在pack目录下
		doservice.shell("openssl enc -aes-256-ecb -in /www/wwwroot/rule/pack/ids_rule_" + date + ".tgz -kfile  ./pack/key -out  ./pack/ids_rule_"+ date + ".nif");
		//删除临时文件
		doservice.shell("rm -f "+url);
		doservice.shell("rm -f system-event.ver");
		doservice.shell("rm -rf /www/wwwroot/rule/pack/ids_rule_"+date+".tgz");
		String path = "/www/wwwroot/rule/pack/ids_rule_" + date + ".nif";
		if (StringUtils.isNotBlank(path)) {
			FileSystemResource file = new FileSystemResource(path);
			String fileName = new String(file.getFilename().getBytes(), "iso8859-1");
			if (file.exists()) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Content-Disposition", "attachment;fileName=" + fileName);
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
						.contentType(MediaType.parseMediaType("application/octet-stream"))
						.body(new InputStreamResource(file.getInputStream()));
			}
		}
		return null;
	}

	/**
	 * 手动包
	 */
	@GetMapping(value = "/rule/pack/choice")
	public ResponseEntity<InputStreamResource> pack(String nvid, HttpServletRequest req) throws Exception {
		String[] nvids = null ;
		if(nvid.indexOf(",")!=-1){
			nvids = nvid.split(",");
		}else{
			nvids = new String[1];
			nvids[0] = nvid;
		}
		/**
		 * 需求: 1 . 计算规则数量 2. 将打包的ids_flag置为1 3. 复制对应的pcap包(非空判断) 结构: 压缩包
		 * ---|----sql | |--nvid_xxx |----pcap---|---nvid_xxxxx
		 * |----ids_update_Y_M_D_hh_mm.txt 对于txt功能及内容描述: 本次打包将以下漏洞置为了失效:
		 * (对比条件是上次打包的记录中没有,且这次打包的记录也没有,ids_flag=0的) nvid:1,2,3 本次打包了以下漏洞
		 * (对比条件,上次没有打过的,且ids_flag=2) nvid:4,5,6,7 完成打包后,将打包过的ids_flag置为 1(发布)
		 */
		String ids_update_date = doservice.ids_updatePath(nvids);
		if (StringUtils.isNotBlank(ids_update_date)) {
			FileSystemResource file = new FileSystemResource(ids_update_date);
			String fileName = new String(file.getFilename().getBytes(), "iso8859-1");
			if (file.exists()) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
				headers.add("Content-Disposition", "attachment;fileName=" + fileName);
				headers.add("Pragma", "no-cache");
				headers.add("Expires", "0");
				return ResponseEntity.ok().headers(headers).contentLength(file.contentLength())
						.contentType(MediaType.parseMediaType("application/octet-stream"))
						.body(new InputStreamResource(file.getInputStream()));
			}
		}
		return null;
	}

}
