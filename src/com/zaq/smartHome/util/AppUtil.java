package com.zaq.smartHome.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.PropertyConfigurator;

public class AppUtil {
	private static String log4j="log4j.properties";
	private static String config="config.properties";
	private static Properties props ;
	
	public static void init() throws IOException {

		PropertyConfigurator.configure(PathUtil.instance().getPath(log4j)); 
		
		props= new Properties();
		InputStream isConfig = new BufferedInputStream(AppUtil.class.getClassLoader().getResourceAsStream(config));

		props.load(isConfig);
		
	}

	public static String getPropertity(String string) {
		return props.getProperty(string, "");
	}

	public static int getTimeOut(){
		return Integer.valueOf(getPropertity("http.timeout"));
	}
	
	public static int getMaxError(){
		return Integer.valueOf(getPropertity("http.post.maxError"));
	}
	
}	
