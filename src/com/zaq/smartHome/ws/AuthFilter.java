package com.zaq.smartHome.ws;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;


/**
 * 后续采用过滤器的方式进行登陆检测
 * @author zaqzaq
 * 2015年12月13日
 *
 */
@Component("authFilter")  
public class AuthFilter implements Filter{


	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}  
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("检测请求头中的用户名密码doFilter.....");
		
		chain.doFilter(req, res);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}