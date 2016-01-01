package com.zaq.smartHome;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;

import com.zaq.smartHome.pi4j.been.Been;
import com.zaq.smartHome.pi4j.bodyInfrared.BodyInfrared;
import com.zaq.smartHome.pi4j.csb.Csb;
import com.zaq.smartHome.pi4j.diode.Diode;
import com.zaq.smartHome.sound.AudioUtil;
import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.BDUtil;
import com.zaq.smartHome.util.HttpPoolUtil;

/**
 * 程序主入口
 * @author zaqzaq
 * 2015年12月5日
 *
 */
@SpringBootApplication(scanBasePackages="com.zaq.smartHome.ws",exclude=FreeMarkerAutoConfiguration.class)
//@Configuration  
//@ComponentScan(basePackages="com.zaq.smartHome.ws")
//@EnableAutoConfiguration(exclude=FreeMarkerAutoConfiguration.class)
public class RunMain extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
	
	public static void main(String[] args) throws IOException, InterruptedException {
		AppUtil.init();//系统环境初始化
		
		SpringApplication.run(RunMain.class);  //运行WEB
		
		Csb.initOrGet();//初始化超声波检测
		Been.initOrGet();//初始化轰鸣器
		Diode.initOrGet();//初始化发光二极管
		BodyInfrared.initOrGet().listener();//初始化并监听人体红外设备
		
		AudioUtil.playAppInit();//播放系统启动完成的语音提示
		
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
