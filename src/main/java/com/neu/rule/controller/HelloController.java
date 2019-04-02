package com.neu.rule.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;



/**
 * @RestController的意思就是controller里面的方法都以json格式输出，不用再写什么jackjson配置的了！ @author
 *                                                                   Administrator
 * @RequestMapping :属性 method=RequestMethod.GET produces=text/html;charset=UTF-8
 *                 produces=application/json;charset=UTF-8
 * @CrossOrigin : 解决跨域请求 这里的设置是允许所有跨域访问， 也可以单独指定允许的服务器跨域（设置origin的值便可）。
 */

/*
 * 放在类外的映射,要访问到类里面的方法,需要 组合起来 ,例如 localhost:8080/neu/hello
 * 
 * @RequestMapping("/neu")
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class HelloController {
//	private static final String SUECCES = "SUCCESS";
//	private static final String FAIL = "FAIL";

	/*
	 * 读取application.yml 中的属性 , 属性名为userSex 也可以通过类来读取, 详见 /beans/UserProperties
	 * 
	 * @Value("${userSex}") private String userSex;
	 */
	/* 通过类来读取配置文件 */
//	@Autowired
//	private UserProperties userProperties;

//	/*
//	 * 可以让多个域名映射到同一方法
//	 * 
//	 * @RequestMapping(value = {"/hello","/hi"})
//	 */
//	@RequestMapping(value = "/hello", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
//	public String index() {
//		return "hello world" + userProperties.getContent();
//	}

	/***
	 * 获取get方法中的参数
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	// @GetMapping
	// @PostMapping

	/**
	 * @PutMapping("/users/{id}") public User updUserById(@PathVariable Long
	 * id,@RequestParam("name") String name){ User user =
	 * userRepository.findOne(id);//先查出来，否则修改的时候会将其他request中没有的参数也给覆盖掉
	 * user.setName(name); return userRepository.save(user);//与保存是同一个方法 	 * 
	 * @param userName
	 * @param passWord
	 * @return
	 */

//	@RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
//	// 直接形参,只适用于GET 方法,且必须和GET中的形参名保持一致
//	// 或者加注解 public String login(@RequestParam("userName")String name,String
//	// passWord){
//	// 注解只需要注解中的参数名和url中一致, String name 的变量名可以随意.
//	// @RequestParam(value = "userName" required= false,defaultValue="123") 支持
//	// GET 和 POST,当值为空的时候还可以设置默认值
//	public String login(String userName, String passWord) {
//		System.out.println(userName);
//		System.out.println(passWord);
//		String us = "root";
//		String pw = "123456";
//
//		try {
//			if (us.equals(userName) && pw.equals(passWord))
//				return SUECCES;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return FAIL;
//
//	}
}
