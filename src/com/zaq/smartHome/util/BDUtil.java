package com.zaq.smartHome.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.MapUtils;
import org.json.JSONObject;
/**
 * 百度Api调用统一处理类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class BDUtil {
	private static Map<String, AtomicInteger> URL_POST_ERR_COUNT=new ConcurrentHashMap<>();

	public static void main(String[] args) {
		URL_POST_ERR_COUNT.put("xxx1", new AtomicInteger(1));
		URL_POST_ERR_COUNT.put("xxx2", new AtomicInteger(2));
		MapUtils.debugPrint(System.out, "^^^", URL_POST_ERR_COUNT);
	}
	
	/**
	 * 获取 Token
	 * @return
	 * @throws Exception
	 */
    private static String getToken() throws Exception {
        String getTokenURL = "http://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials" + 
            "&client_id=" + AppUtil.getPropertity("apiKey") + "&client_secret=" + AppUtil.getPropertity("secretKey") ;
        HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL).openConnection();
        return  new JSONObject(printResponse(conn)).getString("access_token");
    }
    
    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }
	
	/**
	 * 统一的网页请求 处理 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public  static String httpPost(String url,Map<String, String> args) throws Exception{
		System.out.println("请求："+url);
		
		try {
			
		} catch (Exception e) {
			if(URL_POST_ERR_COUNT.containsKey(url)){
				if(URL_POST_ERR_COUNT.get(url).intValue()>=AppUtil.getMaxError()){
					throw e;
				}else{
					URL_POST_ERR_COUNT.get(url).incrementAndGet();
				}
			}else{
				URL_POST_ERR_COUNT.put(url, new AtomicInteger(0));
			}
			
			return httpPost(url,args);
		}
		if(URL_POST_ERR_COUNT.containsKey(url)){
			URL_POST_ERR_COUNT.remove(url);
		}
		return "";
	}
}
