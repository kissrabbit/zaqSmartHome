package com.zaq.smartHome.ws;

import java.util.Properties;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.cache.TemplateLoader;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public FilterRegistrationBean filterRegistrationBean(AuthFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/admin");
		return filterRegistrationBean;
	}

	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter("utf-8", true);
		return characterEncodingFilter;
	}
	
	/***
	 * 整合freemarket
	 * @return
	 */
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver(){
		FreeMarkerViewResolver freeMarkerViewResolver=new FreeMarkerViewResolver();
		freeMarkerViewResolver.setPrefix("ftl/");
		freeMarkerViewResolver.setSuffix(".ftl");
		freeMarkerViewResolver.setContentType("utf-8");
		
		return freeMarkerViewResolver;
	}
	@Bean
	public FreeMarkerConfig freeMarkerConfig(){
		FreeMarkerConfigurer freeMarkerConfig=new FreeMarkerConfigurer();
		freeMarkerConfig.setTemplateLoaderPath("classpath:/view/");
		Properties settings=new Properties();
		settings.setProperty("template_update_delay", "1");
		settings.setProperty("defaultEncoding", "utf-8");
		settings.setProperty("url_escaping_charset", "utf-8");
		settings.setProperty("locale", "zh_CN");
		freeMarkerConfig.setFreemarkerSettings(settings);
		return freeMarkerConfig;
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/");
	}
}