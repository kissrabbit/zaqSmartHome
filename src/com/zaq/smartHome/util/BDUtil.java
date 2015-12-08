package com.zaq.smartHome.util;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.json.JSONObject;
/**
 * 百度Api调用统一处理类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class BDUtil {
	private static Logger logger=Logger.getLogger(BDUtil.class);
	private static String TOK=null;
	private static Timer timer=new Timer();
	/**
	 * 获取 Token
	 * @return
	 * @throws Exception
	 */
    public static String getToken() throws Exception {
    	if(null==TOK){
    		String getTokenURL = "http://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" + 
    	            "&client_id=" + AppUtil.getPropertity("apiKey") + "&client_secret=" + AppUtil.getPropertity("secretKey") ;
    	        JSONObject json= new JSONObject(HttpPoolUtil.postRetStr(getTokenURL));
    	        
    	        long expires_in=json.getLong("expires_in");//过期时间
    	        
    	        TimerTask task=new TimerTask() {
					
					@Override
					public void run() {
						TOK=null;
						try {
							TOK=getToken();
							logger.info("当前获取的tok为："+TOK);
						} catch (Exception e) {
							logger.error("获取token失败", e);
						}
					}
				};
    	        timer.schedule(task, (expires_in-100)*1000);//提前100秒获取tok保险点，原则是30天30*24*60*60秒 
    	        TOK=json.getString("access_token");
    	}
        
        return  TOK;
    }
    
    
    
	public static void main(String[] args) throws Exception {
		AppUtil.init();
		System.out.println(getToken());
	}
}
