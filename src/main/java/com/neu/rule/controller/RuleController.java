package com.neu.rule.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.neu.rule.beans.IdsRule;
import com.neu.rule.dao.RuleDao;
import com.neu.rule.service.RulerService;

import net.sf.json.JSONObject;

@RestController
@CrossOrigin
public class RuleController {
	@Autowired
	private RuleDao ruledao;
	@Autowired
	private RulerService rulerService;

	/**
	 * 插入规则
	 */

	@PostMapping(value = "/rule/addRule")
	public String addRule(HttpServletRequest request) {
		// //System.out.println(request.getContentType());
		IdsRule rule = new IdsRule();
		rule = rulerService.addRule(request);
		// //System.out.println("插入前的rule:"+rule);
		if (rule != null) {
			return String.valueOf(rule.getNvid());
		} else {
			return "0";
		}

	}

	/**
	 * 更新: 先根据nvid 查询到这个实体，再通过获取到的各个值重新赋值，再保存
	 */
	@Modifying(clearAutomatically = true)
	@PostMapping(value = "/rule/updateRule")
	public String updateRule(HttpServletRequest request) {
		IdsRule rule = new IdsRule();
		rule = rulerService.updateRule(request);
		if (rule != null) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 *  上传的文件
	 * @return 返回上传后的文件路径
	 * {@link com.neu.rule.service.RulerServiceImpl#uploadFile(MultipartFile, HttpServletRequest)}
	 */
	@PostMapping(value = "/rule/addRule/upload")
	public String uploadFile(@RequestParam("pcap_url") MultipartFile file, HttpServletRequest request) {
		String url = rulerService.uploadFile(file, request);
		return url;
	}

	/**
	 * 文件下载
	 * 
	 * @param path 下载文件的完整路径
	 * @throws IOException 抛出IO异常
	 */
	@GetMapping(value = "/rule/download")
	public ResponseEntity<InputStreamResource> downLoad(String path, HttpServletRequest req) throws IOException {
		////System.out.println("访问路径：" + req.getRequestURL());
		if (StringUtils.isNotBlank(path)) {
			//System.out.println("path：" + path);
			FileSystemResource file = new FileSystemResource(path);
			String fileName = new String(file.getFilename().getBytes(), "iso8859-1");
			//System.out.println("文件名：" + file.getFilename());
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

	@PostMapping(value = "/rule/addRule/upload/other")
	public String uploadFileOther(@RequestParam("other_path") MultipartFile file, HttpServletRequest request) {
		String url = rulerService.uploadFile(file, request);
		return url;
	}

	/**
	 * 根据用户输入的值进行对应的查询操作
	 * @return 返回分页信息以及Data
	 */
		@PostMapping("/rule/search")
		public Map<String, Object> searchRules(HttpServletRequest request) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = "";
		String wholeStr = "";
		while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
			wholeStr += str;
		}
		JSONObject json = JSONObject.fromObject(wholeStr);// 转化成json对象
		////System.out.println(json);
		int pageNumber = 1;
		int pageSize = 10;
		String order = "DESC";
		String prop = "nvid";
		if (json.optString("pageNumber") != null && json.optString("pageNumber") != "")
			pageNumber = Integer.valueOf(json.optString("pageNumber"));
		if (json.optString("pageSize") != null && json.optString("pageSize") != "") 
			pageSize = Integer.valueOf(json.optString("pageSize"));	
		if (json.has("order")&&StringUtils.isNotEmpty(json.getString("order"))&&json.getString("order")!="null") {
			order = json.getString("order");
		}		
		if (json.has("prop")&&StringUtils.isNotEmpty(json.getString("prop"))&&json.getString("prop")!="null"){
			prop = json.getString("prop");		
		}		
		String protocol="";
		if (json.has("protocol")&&StringUtils.isNotEmpty(json.getString("protocol"))&&json.getString("protocol")!="null") {
			protocol = json.getString("protocol").replaceAll("\\\\", "\\\\\\\\");	;
		}		
		String category="";
		if (json.has("category")&&StringUtils.isNotEmpty(json.getString("category"))&&json.getString("category")!="null") {
			 category = json.getString("category").replaceAll("\\\\", "\\\\\\\\");	;
		}		
		String cn_name="";
		if (json.has("cn_name")&&StringUtils.isNotEmpty(json.getString("cn_name"))&&json.getString("cn_name")!="null") {
			cn_name = json.getString("cn_name").replaceAll("\\\\", "\\\\\\\\");	
		}		
		String cve_index="";
		if (json.has("cve_index")&&StringUtils.isNotEmpty(json.getString("cve_index"))&&json.getString("cve_index")!="null") {
			cve_index = json.getString("cve_index").replaceAll("\\\\", "\\\\\\\\");	;
		}	
		String bugtraq_index="";
		if (json.has("bugtraq_index")&&StringUtils.isNotEmpty(json.getString("bugtraq_index"))&&json.getString("bugtraq_index")!="null") {
			bugtraq_index = json.getString("bugtraq_index").replaceAll("\\\\", "\\\\\\\\");	;
		}		
		String cnvd_index="";
		if (json.has("cnvd_index")&&StringUtils.isNotEmpty(json.getString("cnvd_index"))&&json.getString("cnvd_index")!="null") {
			 cnvd_index = json.getString("cnvd_index").replaceAll("\\\\", "\\\\\\\\");	;
		}		
		String another_index="";
		if (json.has("another_index")&&StringUtils.isNotEmpty(json.getString("another_index"))&&json.getString("another_index")!="null") {
			another_index = json.getString("another_index").replaceAll("\\\\", "\\\\\\\\");	;
		}		
		if(json.has("Invalid")&&StringUtils.isNotEmpty(json.getString("Invalid"))&&json.getString("Invalid")!="null"){
			//0,查询失效
			int ids_flag =0;
			Map<String, Object> result = rulerService.searchRules(pageNumber - 1, pageSize, protocol, category, cn_name,
					cve_index, bugtraq_index, cnvd_index, another_index, order, prop,ids_flag);
			return result;
		}
		Map<String, Object> result = rulerService.searchRules(pageNumber - 1, pageSize, protocol, category, cn_name,
				cve_index, bugtraq_index, cnvd_index, another_index, order, prop);
		/* 第二种分页查询 */
		return result;
	}

	/**
	 * 根据nvid 查询
	 * 
	 * @param nvid nvid值
	 * @return 返回{@code com.neu.rule.beans.IdsRule}
	 */
	@GetMapping(value = "/search/{id}")
	public IdsRule findOne(@PathVariable("id") Integer nvid) {
		return ruledao.findByNvid(nvid);
	}
	
	/**返回漏洞总数*/
	@GetMapping(value = "/search/findall")
	public int findAll() {
		List<IdsRule> list = ruledao.findAll();
		return list.size();
	}

	@GetMapping("/rule/search/nvid")
	public IdsRule searchRulesById(Integer nvid) {
		////System.out.println(nvid);
		if (nvid != null) {
			IdsRule rule = ruledao.findByNvid(nvid);
			return rule;
		}
		return null;
	}

	/**
	 * 根据规则字段(1-9)模糊查询
	 * function = 1 ,只查询{@code com.neu.rule.beans.IdsFindModel} 中的信息
	 * function = 2,查询全部{@code com.neu.rule.beans.IdsRule}信息
	 * @throws IOException 
	 */
	/*前端传递idsflag  0(失效),1(有用---在数据库中即是idsflag=1and idsflag=2),2(全部)*/
	@PostMapping("/rule/search/ruleLike")
	public Map<String, Object> searchRulesByRule(HttpServletRequest request) throws IOException{		
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = "";
		String wholeStr = "";
		while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
			wholeStr += str;
		}
		JSONObject json = JSONObject.fromObject(wholeStr);// 转化成json对象
		////System.out.println(json);
		int pageNumber = 1;
		int pageSize = 10;
		String order = "DESC";
		String prop = "nvid";
		String rule = "";
		if (json.optString("pageNumber") != null && json.optString("pageNumber") != "")
			pageNumber = Integer.valueOf(json.optString("pageNumber"));
		if (json.optString("pageSize") != null && json.optString("pageSize") != "") 
			pageSize = Integer.valueOf(json.optString("pageSize"));	
		if (json.has("order")&&StringUtils.isNotEmpty(json.getString("order"))&&json.getString("order")!="null") 
			order = json.getString("order");
		if (json.has("prop")&&StringUtils.isNotEmpty(json.getString("prop"))&&json.getString("prop")!="null") 
			prop = json.getString("prop");
		if (json.has("rule")&&StringUtils.isNotEmpty(json.getString("rule"))&&json.getString("rule")!="null") 
			rule = json.getString("rule").replaceAll("\\\\", "\\\\\\\\");
		//System.out.println("json:"+json);
		//System.out.println("rule:"+rule);
		int idsflag = json.getInt("idsflag");	
		int function = 1;
		if (idsflag == 3) { // 想要查全部
			function = 3; // 调用方法三
		}
		if (idsflag == 1) { // 查询有用的
			function = 4;
		}
		if(idsflag == 5){ //全文检索搜索的内容
			function = 5;
		}
		Map<String, Object> result = rulerService.searchRulesByRule(pageNumber - 1, pageSize, order, prop, rule,
				function, idsflag);
		//System.out.println(result);
		return result;
	}

	@PostMapping("/rule/search/ruleLike2")
//@GetMapping("/rule/search/ruleLike2")
	/* 此查询不需要用到 idsflag */
//	public Map<String, Object> searchRulesByRule2(@RequestParam(defaultValue = "1") int pageNumber,
//			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "DESC") String order,
//			@RequestParam(defaultValue = "nvid") String prop, String rule,
//			@RequestParam(defaultValue = "2") int idsflag) {
	public Map<String, Object> searchRulesByRule2(HttpServletRequest request) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String str = "";
		String wholeStr = "";
		while ((str = reader.readLine()) != null) {// 一行一行的读取body体里面的内容；
			wholeStr += str;
		}
		JSONObject json = JSONObject.fromObject(wholeStr);// 转化成json对象
		//System.out.println(json);
		int idsflag = 2;
		int pageNumber = 1;
		int pageSize = 10;
		String order = "DESC";
		String prop = "nvid";
		String rule = "";
		if (json.optString("pageNumber") != null && json.optString("pageNumber") != "")
			pageNumber = Integer.valueOf(json.optString("pageNumber"));
		if (json.optString("pageSize") != null && json.optString("pageSize") != "") 
			pageSize = Integer.valueOf(json.optString("pageSize"));	
		if (json.has("order")&&StringUtils.isNotEmpty(json.getString("order"))&&json.getString("order")!="null") 
			order = json.getString("order");
		if (json.has("prop")&&StringUtils.isNotEmpty(json.getString("prop"))&&json.getString("prop")!="null") 
			prop = json.getString("prop");
		if (json.has("rule")&&StringUtils.isNotEmpty(json.getString("rule"))&&json.getString("rule")!="null") 
			rule = json.getString("rule").replaceAll("\\\\", "\\\\\\\\");
		//System.out.println("rule:"+rule);
		int function = 2;
		Map<String, Object> result = rulerService.searchRulesByRule(pageNumber - 1, pageSize, order, prop, rule,
				function, idsflag);
		return result;
	}
	
}
