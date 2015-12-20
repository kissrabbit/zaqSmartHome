package com.zaq.smartHome.qa.robot;

import java.io.IOException;

import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.zaq.smartHome.qa.BaseQA;
import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.HttpPoolUtil;
/**
 * 集成我的机器人小度的问答
 * @author zaqzaq
 * 2015年12月15日
 *
 */
public class QAxiaoDu extends BaseQA {
	private static QAxiaoDu axiaoDu=new QAxiaoDu();
	private QAxiaoDu(){}
	public static QAxiaoDu instance(){
		return axiaoDu;
	}
	
	@Override
	public String askRemote(String question) {
		return HttpPoolUtil.getRetStr(AppUtil.getXDUri(),new BasicHeader("Cookie", AppUtil.getXDCookie()), 
				new BasicNameValuePair("request_query", question));
	}
	
	public static void main(String[] args) {
		try {
			AppUtil.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String re=HttpPoolUtil.getRetStr(AppUtil.getXDUri(),new BasicHeader("Cookie", AppUtil.getXDCookie()), 
					new BasicNameValuePair("request_query", "苏州后天的天气"));
		System.out.println(re);
		
	}
}
