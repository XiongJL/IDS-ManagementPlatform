package com.neu.rule.filter;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*过滤器配置*/
@Configuration
public class WebSecurityConfig   {
	@Bean
	public FilterRegistrationBean<Filter> someFilterRegistration(){
		//新建过滤器注册类
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		//添加过滤器
		registration.setFilter(new UserFilter());
		//设置过滤器的URL模式
		// 可以只过滤重要模块,这里是全过滤
		registration.addUrlPatterns("/*");
		return registration;
	}
}
