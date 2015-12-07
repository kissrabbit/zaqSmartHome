package com.zaq.smartHome.util;

import org.json.JSONObject;
/**
 * 百度Api调用统一处理类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class BDUtil {
	
	/**
	 * 获取 Token
	 * @return
	 * @throws Exception
	 */
    public static String getToken() throws Exception {
        String getTokenURL = "http://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" + 
            "&client_id=" + AppUtil.getPropertity("apiKey") + "&client_secret=" + AppUtil.getPropertity("secretKey") ;
        return  new JSONObject(HttpPoolUtil.postRetStr(getTokenURL)).getString("access_token");
    }
    
	public static void main(String[] args) throws Exception {
		AppUtil.init();
		System.out.println(getToken());
	}
}
