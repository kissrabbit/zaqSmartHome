package com.zaq.smartHome.qa;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.HZUtil;
/**
 * 指令解析工具类
 * @author zaqzaq
 * 2015年12月24日
 *
 */
public class ParseUtil {
	/**
	 * 解析出的指令
	 * @author zaqzaq
	 * 2015年12月24日
	 *
	 */
	public static class TextDelay{
		public int delay; //延时delay秒
		public String cmd;//执行的指令
	}
	
	private final static String S="秒";
	private final static String SS="秒钟";
	private final static String M="分";
	private final static String MM="分钟";
	private final static String H="小时";
	private final static String HH="个小时";
	
	//指令匹配规则
	private final static String regex="(.+?)([十百千万亿一二三四五六七八九]+)((秒钟)|(秒)|(分钟)|(分)|(个小时)|(小时))$";
	public static Pattern pattern;
	
	public static void init(){
		pattern=Pattern.compile(AppUtil.getPropertity("parse.text.delayCmd", regex));
	}
	
	/**
	 * 从中解析出延时的分钟数  命令格式： 指令+Na+分钟|分|小时|秒 
	 * eg: 开灯十一分钟
	 * @param text  文本
	 * @return
	 */
	public static TextDelay text2Cmd(String text){
		Matcher matcher= pattern.matcher(text);
		if(matcher.find()){
			TextDelay textDelay=new TextDelay();
			textDelay.cmd=matcher.group(1);
			
			String unit=matcher.group(3);
			
			int mul=0;
			if(unit.endsWith(S)){
				mul=1;
			}else if(unit.endsWith(SS)){
				mul=1;
			}else if(unit.endsWith(M)){
				mul=60;
			}else if(unit.endsWith(MM)){
				mul=60;
			}else if(unit.endsWith(H)){
				mul=3600;
			}else if(unit.endsWith(HH)){
				mul=3600;
			}
			textDelay.delay=mul*HZUtil.chineseNumber2Int(matcher.group(2));
			
			return textDelay;
		}else{
			return null;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		AppUtil.init(); 
		init();
		Pattern pattern=Pattern.compile(regex);
		
		Matcher matcher= pattern.matcher("开灯十一分");
		
		System.out.println(matcher.find());
		System.out.println(matcher.group());
		System.out.println(matcher.group(1));
		System.out.println(matcher.group(2));
		System.out.println(matcher.group(3));
		
		TextDelay textDelay =text2Cmd("开灯十一分");
		
		System.out.println(textDelay.delay);
		System.out.println(textDelay.cmd);
		
	}
}
