package com.neu.rule.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.neu.rule.beans.IdsRule;

import net.sf.json.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
public interface RulerService {
	// 插入规则
	IdsRule addRule(HttpServletRequest request);
	
	//更新
	IdsRule updateRule(HttpServletRequest request);
	// 上传文件
	String uploadFile(MultipartFile file, HttpServletRequest request);

	// 查询
	Map<String, Object> searchRules(int pageNumber, int pageSize, String protocol, String category, String cn_name,
			String cve_index, String bugtraq_index, String cnvd_index, String another_index, String order, String prop);
	//模糊查询规则
	Map<String, Object> searchRulesByRule(int pageNumber, int pageSize, String order,String prop,
			String rule, int function,int idsflag);
	//近九年统计规则
	Map<String,String> nineRules(List<IdsRule> rules);
	//今年12个月规则统计
	JSONObject Month(List<IdsRule> rules);

	Map<String, Object> searchRules(int pageNumber, int pageSize, String protocol, String category, String cn_name,
			String cve_index, String bugtraq_index, String cnvd_index, String another_index, String order, String prop,
			int ids_flag);

	Map<String, Object> pageableResult(Page<IdsRule> page, int pageNumber, int pageSize);



	
}
