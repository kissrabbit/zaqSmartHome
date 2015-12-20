package com.zaq.smartHome;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.zaq.smartHome.pi4j.been.Been;
import com.zaq.smartHome.pi4j.bodyInfrared.BodyInfrared;
import com.zaq.smartHome.pi4j.csb.Csb;
import com.zaq.smartHome.pi4j.diode.Diode;
import com.zaq.smartHome.util.AppUtil;

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
	
	public static void main(String[] args) throws IOException, InterruptedException {
		AppUtil.init();
		
		//初始化硬件设备
		BodyInfrared.init().listener();
		Csb.init().run();
		Been.init();
		Diode.init();
		
//		SpringApplication.run(RunMain.class);  
		while(true){
			Thread.sleep(5000);
		}
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
