package com.zaq.smartHome.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.zaq.smartHome.qa.ParseUtil;
/**
 * 系统工具类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class AppUtil {
	private static String log4j="log4j.properties";
	private static String config="config.properties";
	private static String configJdbc="jdbc.properties";
	private static String configPi4j="pi4j.properties";
	private static Properties props ;
	
	public static void init() throws IOException {

		PropertyConfigurator.configure(PathUtil.instance().getPath(log4j)); 
		
		props= new Properties();
		InputStream isConfig = new BufferedInputStream(AppUtil.class.getClassLoader().getResourceAsStream(config));
		props.load(isConfig);
		InputStream isConfigJdbc = new BufferedInputStream(AppUtil.class.getClassLoader().getResourceAsStream(configJdbc));
		props.load(isConfigJdbc);
		InputStream isConfigPi4j = new BufferedInputStream(AppUtil.class.getClassLoader().getResourceAsStream(configPi4j));
		props.load(isConfigPi4j);
		
		DbHelper.init();//初始化数据源
		ParseUtil.init();//初始化模式解析工具
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
	
	public static String getXDCookie(){
		return getPropertity("xd.cookie","BDUSS=VZySDVuWjltUzdyZlM4Z0FnRERVQ0lUejRyblFuWFZvbERzTUgxckMzZWRDWmRXQVFBQUFBJCQAAAAAAAAAAAEAAAB1ZkQIMzgyNTY2Njk3AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ18b1adfG9WL");
	}
	public static String getXDUri(){
		return getPropertity("xd.uri","https://sp0.baidu.com/yLsHczq6KgQFm2e88IuM_a/s?sample_name=bear_brain&");
	}
}	
