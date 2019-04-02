package com.neu.rule.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *  过滤器: 每次前端发起请求,都会首先进行身份认证.
 *  处理访问请求头,解决跨域问题
 * @author XiongJL
 *
 */
public class UserFilter implements Filter {
	// 设置未登录信息
	String NO_LOGIN = "nologin";
	// 不需要登录就可以访问的路径(比如:注册登录等) !!!!!!!!!!!!!!!!!!!!!!!!!!!记得取消
	//
	String[] includeUrls = new String[] { "/login", "/login.html", "/login/exist", "/logout","/rule/search/nvid","/rule/search",
			"/rule/addRule","/rule/updateRule","/rule/addRule/upload","/rule/addRule/upload/other","/rule/search/ruleLike",
			"/rule/search/ruleLike2","/search/findall"};
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		// 处理跨域, 以及让前端vue 更改配置 每次ajax请求都请求一样的Session
		//     部署的时候需要修改     地址      
		//实际环境
		response.setHeader("Access-Control-Allow-Origin", "http://192.168.0.35");
		//测试环境
		//response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		//云环境
		//response.setHeader("Access-Control-Allow-Origin", "http://119.3.65.16");
		// 当Access-Control-Allow-Credentials设为true的时候，Access-Control-Allow-Origin不能设为星号，
		//既然不让我设为星号，我就改成前端项目的配置
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Headers", "accept,content-type");
		response.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
		String uri = request.getRequestURI();
		//System.out.println("filter url:" + uri);
		// 是否需要过滤
		boolean needFilter = isNeedFilter(uri);
		if (!needFilter) { // 不需要过滤的直接传给下一个过滤器
			chain.doFilter(req, res);
		} else {
			// session中包含user对象,则是登录状态
			// System.out.println("fileterSession:"+session.getAttribute("user"));
			if (session != null && session.getAttribute("user") != null) {
				//System.out.println("user:" + session.getAttribute("user"));
				//System.out.println("fileterSession:" + session.getAttribute("user"));
				chain.doFilter(req, res);
			} else {
				// 未登录,把状态码发送给前端,让其处理
				// System.out.println("fileterSession:"+session.getAttribute("user"));
				response.getWriter().write(this.NO_LOGIN);
			}
		}
	}

	/**
	 *  是否需要过滤
	 * @param uri 不需要过滤的请求
	 * @return true: 本次请求需要验证  false: 本次请求不需要验证
	 */
	private boolean isNeedFilter(String uri) {
		for (String includeUrl : includeUrls) {
			if (includeUrl.equals(uri)) {
				// 不需要
				return false;
			}
		}
		return true;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
