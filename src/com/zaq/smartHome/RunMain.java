package com.zaq.smartHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 程序主入口
 * @author zaqzaq
 * 2015年12月5日
 *
 */
@Configuration  
@ComponentScan  
@EnableAutoConfiguration 
public class RunMain extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
	
	public static void main(String[] args) {
		//TODO 初始化硬件设备
		
		
		SpringApplication.run(RunMain.class);  
	}

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setContextPath("");  
        container.setPort(8081);  
        container.setSessionTimeout(30);  
        //错误页面配置
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/screen/error")); 
	}
}
