package com.zaq.smartHome.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
/**
 * 系统工具类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class AppUtil {
	private static String log4j="log4j.properties";
	private static String config="config.properties";
	private static Properties props ;
	
	public static void init() throws IOException {

		PropertyConfigurator.configure(PathUtil.instance().getPath(log4j)); 
		
		props= new Properties();
		InputStream isConfig = new BufferedInputStream(AppUtil.class.getClassLoader().getResourceAsStream(config));

		props.load(isConfig);
		
		HttpPoolUtil.init();
	}

	public static String getPropertity(String string) {
		return props.getProperty(string, "");
	}
	
	public static String getPropertity(String string,String dfStr) {
		return props.getProperty(string, dfStr);
	}
	
	public static int getPropertity(String string,int dfInt) {
		return Integer.valueOf(props.getProperty(string, dfInt+""));
	}
	
	public static int getConnTimeOut(){
		return Integer.valueOf(getPropertity("http.conn.timeout",1000));
	}
	
	public static int getReadTimeOut(){
		return Integer.valueOf(getPropertity("http.read.timeout",1000));
	}
	
	public static int getMaxError(){
		return Integer.valueOf(getPropertity("http.post.maxError",10));
	}

}	
