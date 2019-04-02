package com.neu.rule.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neu.rule.beans.User;
import com.neu.rule.dao.UserDao;

/**
 * 用户相关操作
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
public class UserController {
	@Autowired
	private UserDao userDao;
	
	/*登录验证*/
	@GetMapping(value = "/login/exist")
	@CrossOrigin
	public String userExist(String userName, String passWord,HttpServletRequest request){
		//System.out.println("正在查询");
		User user = null;
		try {		
			user = userDao.findByUser(userName);
			//System.out.println(user);
			//此处应该比较的是内容,用equals比较
			if(userName.equals(user.getUser()) && passWord.equals(user.getPassword())){				
				 // 设置Session
				HttpSession session = request.getSession();
				session.setAttribute("user",user.getUser());
				session.setMaxInactiveInterval(86400);//session超时时间，单位为秒
				//System.out.println(user.getUser());
				//System.out.println("session:"+session.getAttribute("user"));
				return "1";
			}else{		
				return "loginfail";	
			}
		} catch (Exception e) {
			//当用户输入空的时候会报异常
			return "loginfail-exception";
		}
		
	}
	
	/*退出登录*/
	@GetMapping(value = "/logout")
	@CrossOrigin
	public String logout(HttpSession session){
		//移除session
		if(session.getAttribute("user")!=null){
			session.removeAttribute("user");
			return "1";
		}else{
			return "0";
		}
		
	}


	
	
	
	
	
	
	
	/*本业务暂不提供注册功能*/
	/*插入用户*/
	/*
	@SuppressWarnings("unused")
	@PostMapping(value="/user/register")
	public String userAdd(@RequestParam("user")String username,
				@RequestParam("password")String password){
		User user = new User();
		user.setUser(username);
		user.setPassword(password);
		if(user ==null){
			return "请确认是否输入正确";
		}else{
			userDao.save(user);
			return  "SUECCES";
		}
	}*/
	
}
