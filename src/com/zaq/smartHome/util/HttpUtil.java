package com.zaq.smartHome.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.zaq.smartHome.exception.HttpMaxErrorException;

/**
 * http请求工具类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class HttpUtil {
	private static Logger  logger=Logger.getLogger(HttpUtil.class);
	/**
	 * post请求错误次数累加器
	 */
	public static Map<String, AtomicInteger> URL_POST_ERR_COUNT=new ConcurrentHashMap<>();
	/**
	 * 组装http请求
	 * @param postUrl 请求的url
	 * @param parm 参数
	 * @return 字符体
	 * @throws HttpMaxErrorException 
	 */
	public static String postRetStr(String postUrl,NameValuePair... parm) throws HttpMaxErrorException{
		if(hasMaxError(postUrl)){
			throw new HttpMaxErrorException(postUrl);
		}
		HttpClient httpClient=getClient();
		PostMethod postMethod=new PostMethod(postUrl);
		postMethod.setRequestBody(parm);
//		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		 String response=null;
		try {
			httpClient.executeMethod(postMethod);
			 if(postMethod.getStatusLine().getStatusCode()!=200){
				 logger.error(postUrl+"请求异常：statusCode="+postMethod.getStatusLine().getStatusCode());
				 return postRetStr(postUrl, parm);
			 }
		      response=new String(postMethod.getResponseBodyAsString().getBytes("UTF-8"));  
		} catch (HttpMaxErrorException e) {
			throw e;
		}  catch (Exception e) {
			logger.error(postUrl+"请求异常",e);
			postMethod.abort();
			return postRetStr(postUrl, parm);
		}
		System.out.println("response:"+response);
		if(URL_POST_ERR_COUNT.containsKey(postUrl)){
			URL_POST_ERR_COUNT.remove(postUrl);
		}
		return response;
	}
	
	
	/**
	 * 组装http请求
	 * @param postUrl 请求的url
	 * @param parm 参数
	 * @return 多媒体文体
	 * @throws HttpMaxErrorException 
	 */
	public static InputStream postRetMulti(String postUrl,NameValuePair... parm) throws HttpMaxErrorException{
		if(hasMaxError(postUrl)){
			throw new HttpMaxErrorException(postUrl);
		}
		HttpClient httpClient=getClient();
		PostMethod postMethod=new PostMethod(postUrl);
		postMethod.setRequestBody(parm);
//		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		postMethod.addRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
		InputStream response=null;
		try {
			httpClient.executeMethod(postMethod);
			 if(postMethod.getStatusLine().getStatusCode()!=200){
				 logger.error(postUrl+"请求异常：statusCode="+postMethod.getStatusLine().getStatusCode());
				 return postRetMulti(postUrl, parm);
			 }
		      response=postMethod.getResponseBodyAsStream();  
		} catch (HttpMaxErrorException e) {
			throw e;
		}  catch (Exception e) {
			logger.error(postUrl+"请求异常",e);
			postMethod.abort();
			return postRetMulti(postUrl, parm);
		}
		logger.debug("response:"+response);
		if(URL_POST_ERR_COUNT.containsKey(postUrl)){
			URL_POST_ERR_COUNT.remove(postUrl);
		}
		return response;
	}
	
	/**
	 * 最大错误请求数计数
	 * @param url
	 * @return
	 */
	public  static boolean hasMaxError(String url){
		if(URL_POST_ERR_COUNT.containsKey(url)){
			if(URL_POST_ERR_COUNT.get(url).intValue()>=AppUtil.getMaxError()){
				URL_POST_ERR_COUNT.remove(url);
				return true;
			}else{
				URL_POST_ERR_COUNT.get(url).incrementAndGet();
			}
		}else{
			URL_POST_ERR_COUNT.put(url, new AtomicInteger(0));
		}
			
		return false;
	}
	
	/**
	 * 获取Client
	 * @return
	 */
	private static HttpClient getClient(){
		HttpClient client=new HttpClient();
		client.getHttpConnectionManager().getParams().setConnectionTimeout(AppUtil.getConnTimeOut());
		client.getHttpConnectionManager().getParams().setSoTimeout(AppUtil.getReadTimeOut());
		
		return client;
	}
	
	
	public static void main(String[] args){
		try {
			AppUtil.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		URL_POST_ERR_COUNT.put("xxx1", new AtomicInteger(1));
		URL_POST_ERR_COUNT.put("xxx2", new AtomicInteger(2));
		MapUtils.debugPrint(System.out, "^^^", URL_POST_ERR_COUNT);
	}
}
