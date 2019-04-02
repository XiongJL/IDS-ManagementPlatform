package com.neu.rule.demo.demo;
/*
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;import org.springframework.web.server.MediaTypeNotSupportedStatusException;

import com.neu.rule.controller.HelloController;
*/
/**
 * 对模块进行集成测试时，希望能够通过输入URL对Controller进行测试， 
 * 如果通过启动服务器，建立http
 * client进行测试，这样会使得测试变得很麻烦，比如，启动速度慢，
 * 测试验证不方便，依赖网络环境等，所以为了可以对Controller进行测试，我们引入了MockMVC。
 * 
 * MockMvc实现了对Http请求的模拟，能够直接使用网络的形式，转换到Controller的调用，
 * 这样可以使得测试速度快、不依赖网络环境，而且提供了一套验证的工具，
 * 这样可以使得请求的验证统一而且很方便。
 * 
 * 
 * @author Administrator
 *
 */
/*
@RunWith(SpringRunner.class)
@SpringBootTest
*/
public class DemoApplicationTests {
//	private MockMvc mvc;
//	
//	@Before
//	public void setUp() throws Exception{
//		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
//	}
//	@Test
//	public void getHello() throws Exception{
//		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andDo(MockMvcResultHandlers.print())
//		.andReturn();
//	}
//	
//	@Test
//	public void contextLoads() {
//	}

}
