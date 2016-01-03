package com.zaq.smartHome.ws;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaq.smartHome.util.Constant;

/**
 * shiro 配置中心
 * @author zaqzaq
 * 2016年1月1日
 *
 */
@Configuration
public class ShiroConfig {
	public static void main(String[] args) {
		System.out.println("freshzcn12345678".length());
		System.out.println(DigestUtils.md5Hex("1"));
		System.out.println(Base64.encodeToString("freshzcn12345678".getBytes()));
	}
	/**
	 * shiro安全认证管理器
	 * @return
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager(){
		DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager(new MyRealm());
		
		CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
		
		SimpleCookie cookie=new SimpleCookie(Constant.REMEMBER_ME);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(31104000);//1年
		
		cookieRememberMeManager.setCookie(cookie);
		cookieRememberMeManager.setCipherKey(Base64.decode("ZnJlc2h6Y24xMjM0NTY3OA=="));//AES密匙
		
		defaultWebSecurityManager.setRememberMeManager(cookieRememberMeManager);
		return defaultWebSecurityManager;
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/");
		shiroFilterFactoryBean.setSuccessUrl("/");
		shiroFilterFactoryBean.setUnauthorizedUrl("/");
		return shiroFilterFactoryBean;
	}
	
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
		LifecycleBeanPostProcessor lifecycleBeanPostProcessor=new LifecycleBeanPostProcessor();
		return lifecycleBeanPostProcessor;
	}
	
	/**
	 * 配置shiro 可用的@require*的注解
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	/**
	 * 配置shiro 可用的@require*的注解
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor( DefaultWebSecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
