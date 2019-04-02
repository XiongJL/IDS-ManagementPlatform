package com.neu.rule.demo.demo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neu.rule.beans.IdsRule;
import com.neu.rule.dao.RuleDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Reflection {
	@Autowired
	private  RuleDao ruledao;
	
	@Test
	public void testReflection() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		IdsRule rule =  ruledao.findLastNvid();
		if(rule.getRule1()!=null&&rule.getRule1()!=""){
			String rule1 = rule.getRule1();
			String sid = "sid:"+String.valueOf(rule.getNvid())+"1;)";
			String rulenew = rule1.replaceAll("\\)$", sid);
			rule.setRule1(rulenew);
		}
	
	}
	@Test
	public void testTeShuZiFu(){
		IdsRule rule =  ruledao.findLastNvid();
		rule.setBugtraq_index("@#$%^&%$@23");
		rule.setRule1("@#$%^&*(@#$%^&*('''')");
		ruledao.save(rule);
	}
	
	@Test
	public void InvalidNvids() throws ParseException, IOException{
		File file = new File("C:\\Users\\Administrator\\Desktop\\demo.war\\1.txt");
		FileOutputStream fos = new FileOutputStream(file);
		PrintWriter out = new PrintWriter(fos);
		SimpleDateFormat a = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
		Date publish1 = a.parse("2018_12_21_00_00");
		long[] InvalidNvids = ruledao.findNvidByPublish_date(publish1, new Date());
		String line = "";
		String id;
		System.out.println("开始循环");
		for(long nid : InvalidNvids){
			id = String.valueOf(nid);
			line += id+",";
		}
		if(line.length()>0){
			line = line.substring(0,line.length()-1);
		}
		System.out.println(line);
		out.println("本次打包将以下漏洞置为失效: ");
		out.print("本次打包将以下漏洞置为失效: \r\n");
		out.println(line);
		System.out.println("长度:"+line.length());
		out.flush();
		fos.close();
	}
}




