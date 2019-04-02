package com.neu.rule.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.neu.rule.beans.IdsFindModel;
import com.neu.rule.beans.IdsRule;
import com.neu.rule.dao.RuleDao;
import com.neu.rule.util.DateFormat;
import com.neu.rule.util.FileUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class RulerServiceImpl implements RulerService {
	@Autowired
	private RuleDao ruledao;

	/**
	 * 插入操作
	 */
	// 事务管理注解
	@Transactional
	public IdsRule addRule(HttpServletRequest request) {
		IdsRule rule = new IdsRule();
		rule = getRuleFromReq(request);
		ruledao.save(rule);

		/**
		 * 添加sid : alert tcp(.......略 rev:1;) 改为 : alert tcp(.......略
		 * rev:1;sid:11;) sdi: nvid进位1 rule2 即 sid:12; 查询最后一个获取插入的nvid
		 */
		rule = ruledao.findLastNvid();
		String ruleNSid = "";
		if (rule.getRule1() != null && rule.getRule1() != "") {
			String rule1 = rule.getRule1();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "1;)";
			// 去除已有的sid
			ruleNSid = rule1.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule1(rulenew);
		}
		if (rule.getRule2() != null && rule.getRule2() != "") {
			String rule2 = rule.getRule2();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "2;)";
			// 去除已有的sid
			ruleNSid = rule2.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule2(rulenew);
		}
		if (rule.getRule3() != null && rule.getRule3() != "") {
			String rule3 = rule.getRule3();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "3;)";
			// 去除已有的sid
			ruleNSid = rule3.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule3(rulenew);
		}
		if (rule.getRule4() != null && rule.getRule4() != "") {
			String rule4 = rule.getRule4();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "4;)";
			// 去除已有的sid
			ruleNSid = rule4.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule4(rulenew);
		}
		if (rule.getRule5() != null && rule.getRule5() != "") {
			String rule5 = rule.getRule5();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "5;)";
			// 去除已有的sid
			ruleNSid = rule5.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule5(rulenew);
		}
		if (rule.getRule6() != null && rule.getRule6() != "") {
			String rule6 = rule.getRule6();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "6;)";
			ruleNSid = rule6.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule6(rulenew);
		}
		if (rule.getRule7() != null && rule.getRule7() != "") {
			String rule7 = rule.getRule7();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "7;)";
			ruleNSid = rule7.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule7(rulenew);
		}
		if (rule.getRule8() != null && rule.getRule8() != "") {
			String rule8 = rule.getRule8();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "8;)";
			ruleNSid = rule8.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule8(rulenew);
		}
		if (rule.getRule9() != null && rule.getRule9() != "") {
			String rule9 = rule.getRule9();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "9;)";
			ruleNSid = rule9.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule9(rulenew);
		}
		// 更新文件夹名字
		String nvids = String.valueOf(rule.getNvid());
		if (rule.getPcap_path1() != null && rule.getPcap_path1() != "") {
			String pathnew = rename(rule.getPcap_path1(), nvids);
			rule.setPcap_path1(pathnew);
		}
		if (rule.getPcap_path2() != null && rule.getPcap_path2() != "") {
			String pathnew = rename(rule.getPcap_path2(), nvids);
			rule.setPcap_path2(pathnew);
		}
		if (rule.getPcap_path3() != null && rule.getPcap_path3() != "") {
			String pathnew = rename(rule.getPcap_path3(), nvids);
			rule.setPcap_path3(pathnew);
		}
		if (rule.getPcap_path4() != null && rule.getPcap_path4() != "") {
			String pathnew = rename(rule.getPcap_path4(), nvids);
			rule.setPcap_path4(pathnew);
		}
		if (rule.getPcap_path5() != null && rule.getPcap_path5() != "") {
			String pathnew = rename(rule.getPcap_path5(), nvids);
			rule.setPcap_path5(pathnew);
		}
		if (rule.getOther_path() != null && rule.getOther_path() != "") {
			String pathnew = rename(rule.getOther_path(), nvids);
			rule.setOther_path(pathnew);
		}

		return rule;
	}

	// 返回修改后的pcap_url
	public String rename(String path, String nvid) {
		//System.out.println("原始完整路径:" + path);
		// windows
		// String[] pathArr = path.split("\\\\");
		// linux
		String[] pathArr = path.split("/");
		String pathnew1 = "";
		String pathnew2 = "";
		String filename = pathArr[pathArr.length - 1];
		// System.out.println("文件名:" + filename);
		for (int i = 0; i < pathArr.length - 2; i++) {
			pathnew2 += pathArr[i] + File.separator;
			// System.out.println(pathArr[i]);
		}
		// linux 路径加前缀
		pathnew2 = File.separator + pathnew2;
		for (int i = 0; i < pathArr.length - 1; i++) {
			pathnew1 += pathArr[i] + File.separator;
		}
		//去除多个  /////开头的路径	
		pathnew2 = pathnew2.replaceAll("^/+", "/");	
		// 去除最后一个分隔符
		pathnew1.substring(0, pathnew1.length() - 1);
		//System.out.println("将要更改的路径:" + pathnew1);
		String pathnew = pathnew2 + nvid;
		//System.out.println("更改后的路径:" + pathnew);
		File ori = new File(pathnew1);
		ori.renameTo(new File(pathnew));
		return pathnew + File.separator + filename;
	}

	/**
	 * 更新
	 */
	@Transactional
	public IdsRule updateRule(HttpServletRequest request) {
		IdsRule rule = new IdsRule();
		rule = getRuleFromReq(request);
		// System.out.println("nvid:");
		String ruleNSid = "";
		if (rule.getRule1() != null && rule.getRule1() != "") {
			String rule1 = rule.getRule1();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "1;)";
			// 去除已有的sid
			ruleNSid = rule1.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule1(rulenew);
		}
		if (rule.getRule2() != null && rule.getRule2() != "") {
			String rule2 = rule.getRule2();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "2;)";
			// 去除已有的sid
			ruleNSid = rule2.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule2(rulenew);
		}
		if (rule.getRule3() != null && rule.getRule3() != "") {
			String rule3 = rule.getRule3();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "3;)";
			// 去除已有的sid
			ruleNSid = rule3.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule3(rulenew);
		}
		if (rule.getRule4() != null && rule.getRule4() != "") {
			String rule4 = rule.getRule4();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "4;)";
			// 去除已有的sid
			ruleNSid = rule4.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule4(rulenew);
		}
		if (rule.getRule5() != null && rule.getRule5() != "") {
			String rule5 = rule.getRule5();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "5;)";
			// 去除已有的sid
			ruleNSid = rule5.replaceAll("sid:\\d*;", "");
			// 添加sid
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule5(rulenew);
		}
		if (rule.getRule6() != null && rule.getRule6() != "") {
			String rule6 = rule.getRule6();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "6;)";
			ruleNSid = rule6.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule6(rulenew);
		}
		if (rule.getRule7() != null && rule.getRule7() != "") {
			String rule7 = rule.getRule7();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "7;)";
			ruleNSid = rule7.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule7(rulenew);
		}
		if (rule.getRule8() != null && rule.getRule8() != "") {
			String rule8 = rule.getRule8();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "8;)";
			ruleNSid = rule8.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule8(rulenew);
		}
		if (rule.getRule9() != null && rule.getRule9() != "") {
			String rule9 = rule.getRule9();
			String sid = "sid:" + String.valueOf(rule.getNvid()) + "9;)";
			ruleNSid = rule9.replaceAll("sid:\\d*;", "");
			String rulenew = ruleNSid.replaceAll("\\)$", sid);
			rule.setRule9(rulenew);
		}
		// 更新文件夹名字
		String nvids = String.valueOf(rule.getNvid());
		if (rule.getPcap_path1() != null && rule.getPcap_path1() != "") {
			String pathnew = rename(rule.getPcap_path1(), nvids);
			rule.setPcap_path1(pathnew);
		}
		if (rule.getPcap_path2() != null && rule.getPcap_path2() != "") {
			String pathnew = rename(rule.getPcap_path2(), nvids);
			rule.setPcap_path2(pathnew);
		}
		if (rule.getPcap_path3() != null && rule.getPcap_path3() != "") {
			String pathnew = rename(rule.getPcap_path3(), nvids);
			rule.setPcap_path3(pathnew);
		}
		if (rule.getPcap_path4() != null && rule.getPcap_path4() != "") {
			String pathnew = rename(rule.getPcap_path4(), nvids);
			rule.setPcap_path4(pathnew);
		}
		if (rule.getPcap_path5() != null && rule.getPcap_path5() != "") {
			String pathnew = rename(rule.getPcap_path5(), nvids);
			rule.setPcap_path5(pathnew);
		}
		if (rule.getOther_path() != null && rule.getOther_path() != "") {
			String pathnew = rename(rule.getOther_path(), nvids);
			rule.setOther_path(pathnew);
		}
		ruledao.save(rule);
		return rule;
	}

	/**
	 * 该方法是对用户上传的文件进行写入本地操作.
	 * 
	 * @return 返回给前端已写入文件的路径
	 */
	public String uploadFile(MultipartFile file, HttpServletRequest request) {
		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		/* 这里或许需要对文件名进行处理,并且路径需要对应 */
		String path1 = "pcap_dir_new/";
		/* 获取最后一条数据库的nvid值 */
		int nvid = ruledao.findLastNvid().getNvid();
		String strid = String.valueOf((nvid + 999999));
		// 建立一个临时目录,在提交后复制到真实nvid目录
		// 同时清空temp 目录
		// System.out.println("创建对象的nvid:" + strid);
		String path = path1 + "/" + strid + "/";
		// 获取web服务器项目的真实物理路径
		String filePath = request.getSession().getServletContext().getRealPath(path);
		// System.out.println("真实路径:" + filePath);
		try {
			FileUtil.createFile(file.getBytes(), filePath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		// 返回前端的JSON
		return filePath + fileName;
	}

	/**
	 * 胶水查询.一个sql语句实现了各种类别查询,以及排序功能,集合在Pageable中.
	 */
	@Override
	public Map<String, Object> searchRules(int pageNumber, int pageSize, String protocol, String category,
			String cn_name, String cve_index, String bugtraq_index, String cnvd_index, String another_index,
			String order, String prop) {
		// System.out.println("开始查询");
		// 根据prop传过来的字段进行排序,默认是nvid , order 默认排序方法ASC
		//System.out.println("pageNumber:" + pageNumber);
		// System.out.println("pageSize:" + pageSize);
		// System.out.println("order:" + order);
		// System.out.println("propR:" + prop);
		// System.out.println("bugtraq_index:"+bugtraq_index);
		Sort sort = new Sort(Sort.Direction.fromString(order), prop);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		// //System.out.println(pageable);
		// Page<IdsRule> page = ruledao.findByName(protocol, category, cn_name,
		// cve_index, bugtraq_index, cnvd_index,
		// another_index, pageable);
		/*
		 * 胶水查询
		 */
		Page<IdsRule> page = (Page<IdsRule>) ruledao.findAll(new Specification<IdsRule>() {
			@Override
			public Predicate toPredicate(Root<IdsRule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotBlank(cn_name)) {
					// System.out.println("root.get:" +
					// root.get("cn_name").as(String.class));
					list.add(cb.like((root.get("cn_name").as(String.class)), "%" + cn_name + "%"));
				}
				if (StringUtils.isNotBlank(protocol)) {
					list.add(cb.like((root.get("protocol").as(String.class)), "%" + protocol + "%"));
				}
				if (StringUtils.isNotBlank(category)) {
					list.add(cb.like((root.get("category").as(String.class)), "%" + category + "%"));
				}
				if (StringUtils.isNotBlank(cve_index)) {
					list.add(cb.like((root.get("cve_index").as(String.class)), "%" + cve_index + "%"));
				}
				if (StringUtils.isNotBlank(bugtraq_index)) {
					list.add(cb.like((root.get("bugtraq_index").as(String.class)), "%" + bugtraq_index + "%"));
				}
				if (StringUtils.isNotBlank(cnvd_index)) {
					list.add(cb.like((root.get("cnvd_index").as(String.class)), "%" + cnvd_index + "%"));
				}
				if (StringUtils.isNotBlank(another_index)) {
					list.add(cb.like((root.get("another_index").as(String.class)), "%" + another_index + "%"));
				}
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		}, pageable);
		// 调用工具类
		Map<String, Object> result = pageableResult(page, pageNumber, pageSize);
		return result;
	}

	@Override
	/* 在失效的漏洞中查询 */
	public Map<String, Object> searchRules(int pageNumber, int pageSize, String protocol, String category,
			String cn_name, String cve_index, String bugtraq_index, String cnvd_index, String another_index,
			String order, String prop, int ids_flag) {
		// 根据prop传过来的字段进行排序,默认是nvid , order 默认排序方法ASC
		// System.out.println("ids_flag:"+ids_flag);
		Sort sort = new Sort(Sort.Direction.fromString(order), prop);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		/*
		 * 胶水查询
		 */
		Page<IdsRule> page = (Page<IdsRule>) ruledao.findAll(new Specification<IdsRule>() {
			@Override
			public Predicate toPredicate(Root<IdsRule> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotBlank(cn_name)) {
					list.add(cb.like((root.get("cn_name").as(String.class)), "%" + cn_name + "%"));
				}
				if (StringUtils.isNotBlank(protocol)) {
					list.add(cb.like((root.get("protocol").as(String.class)), "%" + protocol + "%"));
				}
				if (StringUtils.isNotBlank(category)) {
					list.add(cb.like((root.get("category").as(String.class)), "%" + category + "%"));
				}
				if (StringUtils.isNotBlank(cve_index)) {
					list.add(cb.like((root.get("cve_index").as(String.class)), "%" + cve_index + "%"));
				}
				if (StringUtils.isNotBlank(bugtraq_index)) {
					list.add(cb.like((root.get("bugtraq_index").as(String.class)), "%" + bugtraq_index + "%"));
				}
				if (StringUtils.isNotBlank(cnvd_index)) {
					list.add(cb.like((root.get("cnvd_index").as(String.class)), "%" + cnvd_index + "%"));
				}
				if (StringUtils.isNotBlank(another_index)) {
					list.add(cb.like((root.get("another_index").as(String.class)), "%" + another_index + "%"));
				}
				if (ids_flag == 0) {
					list.add(cb.equal(root.get("ids_flag").as(Integer.class), 0));
				}
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		}, pageable);
		// 调用工具类
		Map<String, Object> result = pageableResult(page, pageNumber, pageSize);
		return result;
	}

	/**
	 * 封装分页信息和数据
	 * 
	 * @param page
	 *            分页类
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页行数
	 * @return 返回当前页数,总页数,每页行数,总行数,以及Data
	 */
	public Map<String, Object> pageableResult(Page<IdsRule> page, int pageNumber, int pageSize) {
		// 查询结果总行数
		long totalElements = page.getTotalElements();
		// System.out.println("总行数:" + totalElements);
		// 按照当前分页大小，总页数
		long totalPages = page.getTotalPages();
		// System.out.println("按照当前分页大小，总页数:" + totalPages);
		// 按照当前页数、分页大小，查出的分页结果集合
		List<IdsRule> list = new ArrayList<IdsRule>();
		for (IdsRule rule : page.getContent()) {
			list.add(rule);
		}
		// 由于数据库中有0000-00-00所以返回json类型时会报错Could not write JSON: Object is null,
		// 但实际有数据,所以这里变成返回map类型
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前分页大小的总页数
		result.put("totalPages", totalPages);
		// 当前第几页
		result.put("pageNumber", page.getNumber());
		// 每页几行
		result.put("pageSize", page.getSize());
		// 存入数据
		result.put("data", list);
		// 总行数
		result.put("totalElements", totalElements);
		return result;
	}

	/**
	 * 模糊查询规则
	 * 
	 * @param prop
	 *            排序的字段
	 * @param order
	 *            排序方式
	 * @param rule
	 *            将要查询的内容
	 * @return 返回分页信息以及Data
	 */
	public Map<String, Object> searchRulesByRule(int pageNumber, int pageSize, String order, String prop, String rule,
			int function, int idsflag) {
		Sort sort = new Sort(Sort.Direction.fromString(order), prop);
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		// 调用工具类
		Map<String, Object> result = null;
		if (function == 1) {
			Page<IdsFindModel> page = ruledao.findByRule(rule, idsflag, pageable);
			result = pageableResultModel(page, pageNumber, pageSize, rule);
		} else if (function == 2) {
			Page<IdsRule> page = ruledao.findByRule2(rule, pageable);
			result = pageableResult(page, pageNumber, pageSize);
		} else if (function == 3) {
			Page<IdsFindModel> page = ruledao.findByRule3(rule, pageable);
			result = pageableResultModel(page, pageNumber, pageSize, rule);
		} else if (function == 4) {
			// 查询ids_flag = 1 and =2 的数据
			int ids_flag = 1;
			int ids_flag2 = 2;
			Page<IdsFindModel> page = ruledao.findByRule4(rule, ids_flag, ids_flag2, pageable);
			result = pageableResultModel(page, pageNumber, pageSize, rule);
		} else if (function == 5) {
			Page<IdsRule> page = ruledao.findByFullSearch(rule, pageable);
			result = pageableResult(page, pageNumber, pageSize);
		}
		return result;
	}

	/**
	 * 查询某几个字段的值
	 * 
	 * @param page
	 *            分页类
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页行数
	 * @param rule
	 *            查询的字符串
	 * @return 返回当前页数,总页数,每页行数,总行数,以及Data
	 */
	public Map<String, Object> pageableResultModel(Page<IdsFindModel> page, int pageNumber, int pageSize, String rule) {
		// 查询结果总行数
		long totalElements = page.getTotalElements();
		// System.out.println("总行数:" + totalElements);
		// 按照当前分页大小，总页数
		long totalPages = page.getTotalPages();
		// System.out.println("按照当前分页大小，总页数:" + totalPages);
		// 按照当前页数、分页大小，查出的分页结果集合
		List<IdsFindModel> list = new ArrayList<IdsFindModel>();
		for (IdsFindModel ids : page.getContent()) {
			if (ids.getRule1() == null || ids.getRule1().equals("")) {
				//// System.out.println("循环中:"+ids.getRule1());
				ids.setRule1("");
			}
			if (ids.getRule2() == null || ids.getRule2().equals("")) {
				ids.setRule2("");
			}
			if (ids.getRule3() == null || ids.getRule3().equals("")) {
				ids.setRule3("");
			}
			if (ids.getRule4() == null || ids.getRule4().equals("")) {
				ids.setRule4("");
			}
			if (ids.getRule5() == null || ids.getRule5().equals("")) {
				ids.setRule5("");
			}
			if (ids.getRule6() == null || ids.getRule6().equals("")) {
				ids.setRule6("");
			}
			if (ids.getRule7() == null || ids.getRule7().equals("")) {
				ids.setRule7("");
			}
			if (ids.getRule8() == null || ids.getRule8().equals("")) {
				ids.setRule8("");
			}
			if (ids.getRule9() == null || ids.getRule9().equals("")) {
				ids.setRule9("");
			}
			list.add(ids);
		}
		// 由于数据库中有0000-00-00所以返回json类型时会报错Could not write JSON: Object is null,
		// 但实际有数据,所以这里变成返回map类型
		Map<String, Object> result = new HashMap<String, Object>();
		// 当前分页大小的总页数
		result.put("totalPages", totalPages);
		// 当前第几页 ,返回时 pageNumber+1
		result.put("pageNumber", pageNumber + 1);
		// 每页几行
		result.put("pageSize", pageSize);
		// 存入数据
		result.put("data", list);
		// 总行数
		result.put("totalElements", totalElements);
		return result;
	}

	@Override
	public Map<String, String> nineRules(List<IdsRule> rules) {
		Calendar cal = Calendar.getInstance();
		// 获取今年的时间
		Calendar now = Calendar.getInstance();
		int nowYear = now.get(Calendar.YEAR);
		// //System.out.println(nowYear);
		// <年,规则数>
		Map<String, String> count = new HashMap<>();
		for (int j = 18; j >= 0; j--) {
			// //System.out.println(j);
			// 计每年的总数
			int countYear = 0;
			for (int i = 0; i < rules.size(); i++) {
				// 获取当条漏洞的规则数量
				int CountRule = 0;
				IdsRule rule = rules.get(i);
				// 获取当前规则的年份
				if (rule.getPublishdate() == null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = null;
					try {
						date = sdf.parse("1949-10-01");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					rule.setPublishdate(date);
				}
				cal.setTime(rule.getPublishdate());
				int year = cal.get(Calendar.YEAR);
				// //System.out.println("year:"+year);
				// 判断该漏洞有多少规则
				if (year == nowYear - j) {
					// 查找该条漏洞的9条规则是否存在
					if (rule.getRule1() != null && rule.getRule1() != "") {
						CountRule++;
					}
					if (rule.getRule2() != null && rule.getRule2() != "") {
						CountRule++;
					}
					if (rule.getRule3() != null && rule.getRule3() != "") {
						CountRule++;
					}
					if (rule.getRule4() != null && rule.getRule4() != "") {
						CountRule++;
					}
					if (rule.getRule5() != null && rule.getRule5() != "") {
						CountRule++;
					}
					if (rule.getRule6() != null && rule.getRule6() != "") {
						CountRule++;
					}
					if (rule.getRule7() != null && rule.getRule7() != "") {
						CountRule++;
					}
					if (rule.getRule8() != null && rule.getRule8() != "") {
						CountRule++;
					}
					if (rule.getRule9() != null && rule.getRule9() != "") {
						CountRule++;
					}
					countYear += CountRule;
					// //System.out.println("countYea:"+countYear);
				}
			}
			// 获取近19年完成
			count.put(Integer.toString((nowYear - j)), Integer.toString(countYear));
		}
		// 获取19年前所有的规则量开始
		int CountRule = 0;
		for (int i = 0; i < rules.size(); i++) {
			IdsRule rule = rules.get(i);
			cal.setTime(rule.getPublishdate());
			int year = cal.get(Calendar.YEAR);
			if (year < nowYear - 18) {
				// 查找该条漏洞的9条规则是否存在
				if (rule.getRule1() != null && rule.getRule1() != "") {
					CountRule++;
				}
				if (rule.getRule2() != null && rule.getRule2() != "") {
					CountRule++;
				}
				if (rule.getRule3() != null && rule.getRule3() != "") {
					CountRule++;
				}
				if (rule.getRule4() != null && rule.getRule4() != "") {
					CountRule++;
				}
				if (rule.getRule5() != null && rule.getRule5() != "") {
					CountRule++;
				}
				if (rule.getRule6() != null && rule.getRule6() != "") {
					CountRule++;
				}
				if (rule.getRule7() != null && rule.getRule7() != "") {
					CountRule++;
				}
				if (rule.getRule8() != null && rule.getRule8() != "") {
					CountRule++;
				}
				if (rule.getRule9() != null && rule.getRule9() != "") {
					CountRule++;
				}
			}
		}
		count.put("更早之前", Integer.toString(CountRule));
		// System.out.println("count:" + count);
		return count;
	}

	@Override
	public JSONObject Month(List<IdsRule> rules) {
		Calendar cal = Calendar.getInstance();
		Map<String, String> count = new HashMap<>();
		// 获取今年的时间
		// 计算每月的规则
		for (int j = 1; j <= 12; j++) {
			// 计数
			int CountRule = 0;
			for (int i = 0; i < rules.size(); i++) {
				IdsRule rule = rules.get(i);
				// 获取当前规则的月份
				cal.setTime(rule.getPublishdate());
				// +1
				int month = cal.get(Calendar.MONTH) + 1;
				if (j == month) {
					// 查找该条漏洞的9条规则是否存在
					if (rule.getRule1() != null && rule.getRule1() != "") {
						CountRule++;
					}
					if (rule.getRule2() != null && rule.getRule2() != "") {
						CountRule++;
					}
					if (rule.getRule3() != null && rule.getRule3() != "") {
						CountRule++;
					}
					if (rule.getRule4() != null && rule.getRule4() != "") {
						CountRule++;
					}
					if (rule.getRule5() != null && rule.getRule5() != "") {
						CountRule++;
					}
					if (rule.getRule6() != null && rule.getRule6() != "") {
						CountRule++;
					}
					if (rule.getRule7() != null && rule.getRule7() != "") {
						CountRule++;
					}
					if (rule.getRule8() != null && rule.getRule8() != "") {
						CountRule++;
					}
					if (rule.getRule9() != null && rule.getRule9() != "") {
						CountRule++;
					}
				}
			}
			count.put(Integer.toString(j), Integer.toString(CountRule));
		}
		JSONObject json = JSONObject.fromObject(count);
		return json;
	}

	/**
	 * 读取前端传递的对象,并转化为 {@code com.neu.rule.beans.IdsRule}
	 * 
	 * @param request
	 *            http请求
	 * @return 返回{@code com.neu.rule.beans.IdsRule}
	 */

	public IdsRule getRuleFromReq(HttpServletRequest request) {
		IdsRule rule = new IdsRule();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String str = "";
			String wholeStr = "";
			while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
				wholeStr += str;
			}
			JSONObject json = JSONObject.fromObject(wholeStr);// 转化成json对象
			// 工具类
			DateFormat df = new DateFormat();
			// 转换string为sql.date类型,
			Date publish_date = df.format(json.getString("publishdate"));
			Date vul_date = df.format(json.getString("vul_date"));
			// 此值主要用于 update操作中,add操作中是没有NVID值的
			if (json.optString("nvid") != null && json.optString("nvid") != "") {
				// System.out.println("nvid:" + json.optString("nvid"));
				rule.setNvid(ruledao.findByNvid(Integer.valueOf(json.optString("nvid"))).getNvid());
			}
			rule.setPopular(Integer.valueOf(json.optString("popular")));
			rule.setIds_flag(Integer.valueOf(json.optString("ids_flag")));
			rule.setIds_origin(Integer.valueOf(json.optString("ids_origin")));
			rule.setVul_date(vul_date);
			rule.setPublishdate(publish_date);
			rule.setLocation(json.getString("location"));
			rule.setAttack_target(json.getString("attack_target"));
			rule.setCategory(json.getString("category"));
			rule.setRisk(json.getString("risk"));
			rule.setVul_status(json.getString("vul_status"));
			rule.setProtocol(json.getString("protocol"));
			rule.setTarget_system(json.getString("target_system"));
			rule.setEn_des(json.getString("en_des"));
			rule.setCn_des(json.getString("cn_des"));
			rule.setNi_test(json.getString("ni_test"));
			rule.setPatch(json.getString("patch"));
			rule.setPatchen(json.getString("patchen"));
			rule.setMome(json.getString("mome"));
			rule.setCve_index(json.getString("cve_index"));
			String cve_index = json.getString("cve_index");
			if (cve_index == null || cve_index == "null" || cve_index == "\"null\"") {
				cve_index = "";
			}
			String cn_name = json.getString("cn_name");
			String en_name = json.getString("english_name");
			/* 添加功能: 给cn_name,en_name加上cve_index */
			String pattern = ".*CVE-.*";
			if (cve_index != null && !cve_index.equals("") && !Pattern.matches(pattern, cn_name)) {
				cn_name = cn_name + " (" + cve_index + ")";
			}
			if (cve_index != null && !cve_index.equals("") && !Pattern.matches(pattern, en_name)) {
				en_name = en_name + " (" + cve_index + ")";
			}
			rule.setCn_name(cn_name);
			rule.setDefault_enable(1); //默认为1
			rule.setEnglish_name(en_name);
			rule.setBugtraq_index(json.getString("bugtraq_index"));
			rule.setCnvd_index(json.getString("cnvd_index"));
			rule.setCurrent(json.getString("current"));
			/* 处理规则json数组 */
			JSONArray rules = JSONArray.fromObject(json.getString("rule"));
			// System.out.println("rules:-----" + rules);
			Iterator<Object> it = rules.iterator();
			int times = 1;
			while (it.hasNext()) {
				JSONObject ruleNext = (JSONObject) it.next();
				if (ruleNext.getString("value") != null && ruleNext.getString("value") != "") {
					switch (times) {
					case 1:
						rule.setRule1(ruleNext.getString("value"));
						break;
					case 2:
						rule.setRule2(ruleNext.getString("value"));
						break;
					case 3:
						rule.setRule3(ruleNext.getString("value"));
						break;
					case 4:
						rule.setRule4(ruleNext.getString("value"));
						break;
					case 5:
						rule.setRule5(ruleNext.getString("value"));
						break;
					case 6:
						rule.setRule6(ruleNext.getString("value"));
						break;
					case 7:
						rule.setRule7(ruleNext.getString("value"));
						break;
					case 8:
						rule.setRule8(ruleNext.getString("value"));
						break;
					case 9:
						rule.setRule9(ruleNext.getString("value"));
						break;
					}
					times++;
				}
			}
			/* 处理规则json数组结束 */
			/* 处理pcap_url json数组开始 */
			// System.out.println("pcap_rul" + json.getString("pcap_url"));
			// System.out.println("paca2" + rule.getPcap_path2());
			JSONArray pcapurls = JSONArray.fromObject(json.getString("pcap_url"));
			Iterator<Object> itpaca = pcapurls.iterator();
			int pacatimes = 1;
			while (itpaca.hasNext()) {
				JSONObject pcapurlsNext = (JSONObject) itpaca.next();
				if (pcapurlsNext.getString("name") != null && pcapurlsNext.getString("url") != null
						&& "nologin".equals(pcapurlsNext.getString("url")) == false) {
					String url = pcapurlsNext.getString("url");
					// System.out.println("前端传来的url:" + url);
					switch (pacatimes) {
					case 1:
						rule.setPcap_path1(url);
						break;
					case 2:
						rule.setPcap_path2(url);
						break;
					case 3:
						rule.setPcap_path3(url);
						break;
					case 4:
						rule.setPcap_path4(url);
						break;
					case 5:
						rule.setPcap_path5(url);
						break;
					}
					pacatimes++;
				}
			}
			/* 处理pcap_url json数组结束 */
			/* 处理other_path json 对象开始/ 前端限制只能上传一个文件 */
			// System.out.println("other_path" + json.getString("other_path"));
			JSONArray otherurls = JSONArray.fromObject(json.getString("other_path"));

			// //System.out.println("otherurls:"+otherurls);
			// 非空判断
			if (otherurls.isEmpty()) {
				// 设置空
				rule.setOther_path("");
			} else {
				Iterator<Object> itother = otherurls.iterator();
				while (itother.hasNext()) {
					JSONObject otherNext = (JSONObject) itother.next();
					String otherUrl = otherNext.getString("url");
					rule.setOther_path(otherUrl);
				}
			}
			/* 处理other_path json对象结束 */
			// 输出获取到的规则
			// System.out.println("lastrule:" + rule);
			return rule;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

	}
}
