package com.neu.rule.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neu.rule.beans.CountCategoryModel;
import com.neu.rule.beans.CountProtocolModel;
import com.neu.rule.beans.CountRiskModel;
import com.neu.rule.beans.CountWeekModel;
import com.neu.rule.beans.IdsRule;
import com.neu.rule.dao.RuleDao;
import com.neu.rule.service.RulerService;
import com.neu.rule.util.DateFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RestController
@CrossOrigin
public class InfoController {
	@Autowired
	private RuleDao ruledao;
	@Autowired
	private RulerService rulerService;

	/**分页查询一段时间内的规则*/
	@PostMapping("/rule/info/publish")
	public Map<String, Object> findByPublish(HttpServletRequest request) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = "";
		String wholeStr = "";
		while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
			wholeStr += str;
		}
		JSONObject json = JSONObject.fromObject(wholeStr);// 转化成json对象
		int pageNumber = 1;
		int pageSize = 10;
		String order = "DESC";
		String prop = "nvid";	
		DateFormat df = new DateFormat();
		Date publishdate1 = null;
		Date publishdate2 = null;
		if (json.optString("pageNumber") != null && json.optString("pageNumber") != "")
			pageNumber = Integer.valueOf(json.optString("pageNumber"));
		if (json.optString("pageSize") != null && json.optString("pageSize") != "") 
			pageSize = Integer.valueOf(json.optString("pageSize"));	
		if (json.has("order")&&StringUtils.isNotEmpty(json.getString("order"))&&json.getString("order")!="null") {
			order = json.getString("order");
		}		
		if (json.has("prop")&&StringUtils.isNotEmpty(json.getString("prop"))&&json.getString("prop")!="null"){
			prop = json.getString("prop");	
			if(prop.equals("publishdate")){
				prop = "publish_date";
			}
		}		
		if(json.has("nvid")&&StringUtils.isNotEmpty(json.getString("nvid"))){
			int nvid = Integer.valueOf(json.getString("nvid"));
			IdsRule rule = ruledao.findByNvid(nvid);
			Map<String, Object> result = new HashMap<>();
			List<IdsRule> data = new ArrayList<>();
			data.add(rule);
			result.put("nivd", nvid);
			result.put("data", data);
			return result;
		}
		if (json.has("publishdate1")&&StringUtils.isNotEmpty(json.getString("publishdate1"))) {
			publishdate1 = df.format(json.getString("publishdate1"));
			//System.out.println("日期1"+publishdate1);
		}else{
			return null;
		}
		if(json.has("publishdate2")&&StringUtils.isNotEmpty(json.getString("publishdate2"))){
			publishdate2 = df.format(json.getString("publishdate2"));
			//System.out.println("日期2"+publishdate2);
		}else{
			return null; 
		}
		Sort sort = new Sort(Sort.Direction.fromString(order), prop);
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize, sort);
		Page<IdsRule> page = ruledao.findBetweenPublish_date(publishdate1,publishdate2,pageable);	
		//System.out.println("条数"+page.getTotalElements());
		Map<String, Object> result = rulerService.pageableResult(page, pageNumber-1, pageSize);
		return result;	
	}
	/**
	 * 最近20条漏洞信息
	 */
	@GetMapping("/rule/info/recently")
	@CrossOrigin
	public	List<Map<String, Object>> infoRecently() {
		List<IdsRule> rules = ruledao.findByPublish_date();
		List<Map<String, Object>> list = new ArrayList<>();	
		//今日新增的漏洞数量
        List<IdsRule> number = ruledao.todayTotal();
        Map<String,Object> todayTotal = new HashMap<>();
        todayTotal.put("todayTotal", number.size());
		list.add(todayTotal);
		for(int i=0;i<rules.size();i++){
			Map<String,Object> map = new HashMap<>();
	        IdsRule rule = rules.get(i);
	        Date date  = rule.getPublishdate();
	        //System.out.println(date);
	        String dateStr = "";
	        if(date!=null){
	        	String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(date).toString().split("-");
	 	        Integer year = Integer.parseInt(strNow[0]);
	 	        Integer month = Integer.parseInt(strNow[1]);
	 	        Integer day = Integer.parseInt(strNow[2]);
	 	       dateStr =  year+"-"+month+"-"+day;
	        }
	        map.put("nvid", rule.getNvid());
		    map.put("cn_name",rule.getCn_name());
		    map.put("enlish_name", rule.getEnglish_name());
		    map.put("publish_date", dateStr);    
		    list.add(map);
	    }
		
		return list;
	}

	/**
	 * 近19年的,每年规则量 ,以及19前年所有的规则量作为一个值
	 */
	@GetMapping("/rule/info/nine")
	@CrossOrigin
	public JSONObject infoNine(){
		List<IdsRule> rules = ruledao.findAll();
		Map<String,String> count = rulerService.nineRules(rules);
		JSONObject json =JSONObject.fromObject(count);
		return json;
	}
	/**
	 *今年每月新增
	 */
	@GetMapping("/rule/info/Month")
	@CrossOrigin
	public JSONObject infoMonth(){
		//获取今年的所有漏洞
		List<IdsRule> rules = ruledao.findByNow();
		JSONObject json = rulerService.Month(rules);
		return json;
	}
	/**
	 * 统计不同协议类型的漏洞数量
	 */
	@GetMapping("/rule/info/protocol")
	@CrossOrigin
	public JSONArray infoProtocol(){
		List<CountProtocolModel> counts = ruledao.countByProtocol();
		JSONArray json = JSONArray.fromObject(counts);
		return json;	
	}
	/**
	 * 统计不同漏洞原因或类型的漏洞数量
	 * 并给前十设置flag = true
	 */
	@GetMapping("/rule/info/category")
	@CrossOrigin
	public JSONArray infoCategory(){
		List<CountCategoryModel> counts = ruledao.countByCategory();
		Collections.sort(counts, new Comparator<CountCategoryModel>(){
			@Override
			public int compare(CountCategoryModel o1, CountCategoryModel o2) {
				return (int) (o2.getValue()-o1.getValue());
			}
		});
		//取前十个设置为true
		List<CountCategoryModel> trueList = counts.subList(0,10);
		for(CountCategoryModel  ccm:trueList){
			ccm.setTop(true);
		}
		JSONArray json = JSONArray.fromObject(counts);
		return json;	
	}
	/**
	 * 统计风险等级
	 */
	@GetMapping("/rule/info/risk")
	@CrossOrigin
	public JSONArray infoRisk(){
		List<CountRiskModel> counts = ruledao.countByRisk();
		Iterator<CountRiskModel> it = counts.iterator();
        while (it.hasNext())
        {
        	CountRiskModel userObj = it.next();
            if (userObj.getName().equals("未知"))
            {
                it.remove();
            }
        }
		JSONArray json = JSONArray.fromObject(counts);
		//return json;	
		return json;
	}
	/**
	 *  近八周(56天)的数据
	 */
	@GetMapping("/rule/info/week")
	@CrossOrigin
	public long[] infoWeek(){
		long[] counts =ruledao.countByWeek();	
		if(counts.length<8){
			long[] arr = new long[8];
			int start = 8-counts.length;
			for (int i = 0; i < counts.length; i++) {
				arr[start] = counts[i];
				start++;
			}
			return arr;
		}else{
			return counts;
		}
		
	}
}
