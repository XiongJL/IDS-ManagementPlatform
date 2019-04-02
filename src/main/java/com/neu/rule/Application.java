package com.neu.rule;
/**
 * 配置spring 扫描路径
 * @ComponentScan(basePackages = {"com.neu.rule.*"})
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
