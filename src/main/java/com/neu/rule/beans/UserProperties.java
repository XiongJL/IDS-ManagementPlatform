package com.neu.rule.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 此类是读取配置文件中的属性,在一些情况下可用这种方式读取application.yml
 * @author Administrator
 *
 */
@Component
@ConfigurationProperties(prefix="user")
public class UserProperties {
	private String userSex;
	private String content;
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
