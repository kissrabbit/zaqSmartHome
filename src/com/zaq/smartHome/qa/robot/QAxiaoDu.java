package com.zaq.smartHome.qa.robot;

import java.io.IOException;

import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

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
		logger.debug("请求问题："+question);
		String httpRes=HttpPoolUtil.getRetStr(AppUtil.getXDUri(),new BasicHeader("Cookie", AppUtil.getXDCookie()), 
				new BasicNameValuePair("request_query", question));
		logger.debug("问题："+question+"\r\n返回："+httpRes);
		//		return new JSONObject(httpRes).getJSONArray("result_list").getJSONObject(0).getJSONObject("result_content").getString("answer");
		String ask=new JSONObject(new JSONObject(httpRes).getJSONArray("result_list").getJSONObject(0).get("result_content").toString()).getString("answer");
		return ask.replaceAll("小度", "那B小心").replaceAll("百度", "果果");
	}
	
	public static void main(String[] args) {
		try {
			AppUtil.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long start=System.currentTimeMillis();
		HttpPoolUtil.getClientByPool();
		System.out.println("cost1:"+ (System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		HttpPoolUtil.getClientByPool();
		System.out.println("cost2:"+ (System.currentTimeMillis()-start));
		String re=HttpPoolUtil.getRetStr(AppUtil.getXDUri(),new BasicHeader("Cookie", AppUtil.getXDCookie()), 
					new BasicNameValuePair("request_query", "你是谁，")
//					new BasicNameValuePair("request_query", "苏州后天的天气")
		);
		
		
		System.out.println(re);
		System.out.println(new JSONObject(re).getJSONArray("result_list").getJSONObject(0).get("result_content"));
		System.out.println(new JSONObject(new JSONObject(re).getJSONArray("result_list").getJSONObject(0).get("result_content").toString()).getString("answer"));
		
//		System.out.println(new JSONObject(re).getJSONArray("result_list").getJSONObject(0).getJSONObject("result_content").getString("answer"));
		
	}
}
