package com.zaq.smartHome.ws;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.boot.ApplicationPid;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * spring boot 配置类
 * @author zaqzaq
 * 2015年12月30日
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter  {
	private Logger logger=Logger.getLogger(WebConfig.class);
	
	/**
	 * 扩展filter
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean(AuthFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/admin");
		return filterRegistrationBean;
	}
	
	/**
	 * 编码过滤器
	 * @return
	 */
	@Bean
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter characterEncodingFilter=new CharacterEncodingFilter("utf-8", true);
		return characterEncodingFilter;
	}
	
	/**
	 * 输入主程的PID
	 * @return
	 */
	@Bean
	public ApplicationPid applicationPid(){
		ApplicationPid applicationPid=new ApplicationPid();
		try {
			applicationPid.write(new File("pid"));
		} catch (IOException e) {
			logger.warn("pid文件输出失败", e);
		}
		return applicationPid;
	}
	
	/***
	 * 整合freemarket
	 */
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver(){
		FreeMarkerViewResolver freeMarkerViewResolver=new FreeMarkerViewResolver();
		freeMarkerViewResolver.setPrefix("ftl/");
		freeMarkerViewResolver.setSuffix(".ftl");
		//解决中文乱码
		freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
		
		return freeMarkerViewResolver;
	}
	/***
	 * 整合freemarket
	 */
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
	
	/**
	 * 静态资源映射
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/");
	}
	
	
	/**
	 * 解决中文乱码问题
	 */
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
		converters.add(stringHttpMessageConverter());
		super.extendMessageConverters(converters);
	}
	
	/**解决 返回JSON 中文乱码
	 */
	private MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
	  MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
	  ObjectMapper objectMapper = new ObjectMapper();
	  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	  List<MediaType> supportedMediaTypes=new ArrayList<>();
	  supportedMediaTypes.add(new MediaType("application","json", Charset.forName("UTF-8")));
	  jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
	  jsonConverter.setObjectMapper(objectMapper);
	  return jsonConverter;
	 }
	
	/**解决 返回String 中文乱码
	 */
	private StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		  List<MediaType> supportedMediaTypes=new ArrayList<>();
		  supportedMediaTypes.add(new MediaType("text","html", Charset.forName("UTF-8")));
		  stringConverter.setSupportedMediaTypes(supportedMediaTypes);
		  return stringConverter;
	 }
}