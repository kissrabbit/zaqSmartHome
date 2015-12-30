package com.zaq.smartHome.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.zaq.smartHome.exception.HttpMaxErrorException;
import com.zaq.smartHome.exception.HttpRequestException;

/**
 * http请求工具类
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class HttpPoolUtil {
	private static Logger  logger=Logger.getLogger(HttpPoolUtil.class);
	private static HttpClientBuilder httpClientBuilder; 
	/**
	 * 连接池配置
	 */
	public static void init(){
		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
	    LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
	   
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			     //信任所有 https
			     public boolean isTrusted(X509Certificate[] chain,
			                     String authType) throws CertificateException {
			         return true;
			     }
			 }).build();
			
			sslsf = new SSLConnectionSocketFactory(sslContext);
			
		} catch (Exception e) {
			logger.error("https请求初始化失败", e);
		}
	    Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", plainsf)
	            .register("https", sslsf)
	            .build();
	    
	    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
	    // 将最大连接数增加到http.pool.maxTotal
	    cm.setMaxTotal(AppUtil.getPropertity("http.pool.maxTotal",200));
	    // 将每个路由基础的连接增加到http.pool.MaxPerRoute
	    cm.setDefaultMaxPerRoute(AppUtil.getPropertity("http.pool.MaxPerRoute",20));
	    // 将目标主机的最大连接数增加到50
	//        HttpHost localhost = new HttpHost("http://www.baidu.com",80);
	//        cm.setMaxPerRoute(new HttpRoute(localhost), 50);
	  //请求重试处理
	    HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
	        public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
	            if (executionCount >= AppUtil.getMaxError()) {// 如果已经重试了maxError次，就放弃                    
	                return false;
	            }
	            if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试                    
	                return true;
	            }
	            if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常                    
	                return false;
	            }                
	            if (exception instanceof InterruptedIOException) {// 超时                    
	                return true;
	            }
	            if (exception instanceof UnknownHostException) {// 目标服务器不可达                    
	                return true;
	            }
	            if (exception instanceof ConnectTimeoutException) {// 连接被拒绝                    
	                return true;
	            }
	            if (exception instanceof SSLException) {// ssl握手异常                    
	                return false;
	            }
	             
	            HttpClientContext clientContext = HttpClientContext.adapt(context);
	            HttpRequest request = clientContext.getRequest();
	            // 如果请求是幂等的，就再次尝试
	            if (!(request instanceof HttpEntityEnclosingRequest)) {                    
	                return true;
	            }
	            return false;
	        }
	    };  
	    
	    RequestConfig  defaultRequestConfig = RequestConfig.custom()
  			  .setSocketTimeout(AppUtil.getReadTimeOut())
  			  .setConnectTimeout(AppUtil.getConnTimeOut())
  			  .setConnectionRequestTimeout(5000)
  			  .build();
	    httpClientBuilder=HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig)
                .setConnectionManager(cm)
                .setUserAgent(AppUtil.getPropertity("http.useragent"))
                .setRetryHandler(httpRequestRetryHandler);
    }
	/**
	 * 组装http请求
	 * @param postUrl 请求的url
	 * @param parm 参数
	 * @return 字符体
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws HttpMaxErrorException 
	 */
	public static String postRetStr(String postUrl,NameValuePair... parm) {
			try {
				return EntityUtils.toString(post(postUrl, parm),"UTF-8");
			} catch (ParseException | IOException | HttpRequestException e) {
				logger.error("请求："+postUrl+" 异常",e);
				return "";
			}
	}
	public static String postRetStr(String postUrl,Header header,NameValuePair... parm) {
		try {
			return EntityUtils.toString(post(postUrl,header, parm),"UTF-8");
		} catch (ParseException | IOException | HttpRequestException e) {
			logger.error("请求："+postUrl+" 异常",e);
			return "";
		}
}
	
	/**
	 * 组装http请求
	 * @param postUrl 请求的url
	 * @param parm 参数
	 * @return 多媒体文体
	 * @throws IOException 
	 * @throws HttpMaxErrorException 
	 */
	public static byte[] postRetMulti(String postUrl,NameValuePair... parm){
			try {
				return  EntityUtils.toByteArray(post(postUrl, parm));
			} catch (IOException | HttpRequestException e) {
				logger.error("请求多媒体文体："+postUrl+" 异常",e);
				return null;
			}
	}
	
	public static String postRetStr(String postUrl,Header header,HttpEntity requestEntity){
		try {
			return  EntityUtils.toString(post(postUrl, header,requestEntity),"UTF-8");
		} catch (IOException | HttpMaxErrorException e) {
			logger.error("请求："+postUrl+" 异常",e);
			return null;
		}
	}
	
	private static HttpEntity post(String postUrl,NameValuePair... parm)throws HttpRequestException{
		return post(postUrl, new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"), parm);
	}
	
	private static HttpEntity post(String postUrl,Header header,NameValuePair... parm)throws HttpRequestException{
		
		try {
			return post(postUrl, header, new UrlEncodedFormEntity(Arrays.asList(parm), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new HttpRequestException(postUrl, e);
		}
	}
	
	private static HttpEntity post(String postUrl,Header header,HttpEntity requestEntity)throws HttpMaxErrorException{
		if(HttpUtil.hasMaxError(postUrl)){
			throw new HttpMaxErrorException(postUrl);
		}
		CloseableHttpClient client=getClientByPool();
		HttpPost request=new HttpPost(postUrl);
		request.addHeader(header);
		HttpEntity retVal=null;
		try {
			request.setEntity(requestEntity);
			CloseableHttpResponse response=client.execute(request);
			if(response.getStatusLine().getStatusCode()!=200){
				logger.error(postUrl+"请求异常：statusCode="+response.getStatusLine().getStatusCode());
				request.abort();
				return post(postUrl,header, requestEntity);
			}
			retVal = response.getEntity();
		} catch (HttpMaxErrorException e) {
			throw e;//防止死循环
		} catch (Exception e) {
			logger.error(postUrl+"请求异常",e);
			request.abort();
			return post(postUrl,header, requestEntity);
		}  
		
		logger.debug("response:"+retVal);
		if(HttpUtil.URL_POST_ERR_COUNT.containsKey(postUrl)){
			HttpUtil.URL_POST_ERR_COUNT.remove(postUrl);
		}
		return retVal;
	}
	
	public static String getRetStr(String getUrl,Header header,NameValuePair... parms){
		try {
			return  EntityUtils.toString(get(getUrl, header,parms),"UTF-8");
		} catch (IOException | HttpMaxErrorException e) {
			logger.error("请求："+getUrl+" 异常",e);
			return null;
		}
	}
	
	private static HttpEntity get(String getUrl,Header header,NameValuePair... parms)throws HttpMaxErrorException{
		if(HttpUtil.hasMaxError(getUrl)){
			throw new HttpMaxErrorException(getUrl);
		}
		CloseableHttpClient client=getClientByPool();
		
		getUrl+=URLEncodedUtils.format(Arrays.asList(parms), "utf-8");
		
		HttpGet request=new HttpGet(getUrl);
		request.addHeader(header);
		HttpEntity retVal=null;
		try {
			CloseableHttpResponse response=client.execute(request);
			if(response.getStatusLine().getStatusCode()!=200){
				logger.error(getUrl+"请求异常：statusCode="+response.getStatusLine().getStatusCode());
				request.abort();
				return get(getUrl,header);
			}
			retVal = response.getEntity();
		} catch (HttpMaxErrorException e) {
			throw e;//防止死循环
		} catch (Exception e) {
			logger.error(getUrl+"请求异常",e);
			request.abort();
			return get(getUrl,header);
		}  
		
		logger.debug("response:"+retVal);
		if(HttpUtil.URL_POST_ERR_COUNT.containsKey(getUrl)){
			HttpUtil.URL_POST_ERR_COUNT.remove(getUrl);
		}
		return retVal;
	}
	
	/**
	 * 从连接池中获取Client
	 * @return
	 */
    public static CloseableHttpClient getClientByPool() {   
         
        return httpClientBuilder.build();
    }  
	
	public static void main(String[] args) {
		try {
			AppUtil.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("xxx:"+postRetStr("http://fanyi.baidu.com/"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String xd="https://sp0.baidu.com/yLsHczq6KgQFm2e88IuM_a/s?sample_name=bear_brain";
		String re=postRetStr(xd,new BasicHeader("Cookie", "BDUSS=VZySDVuWjltUzdyZlM4Z0FnRERVQ0lUejRyblFuWFZvbERzTUgxckMzZWRDWmRXQVFBQUFBJCQAAAAAAAAAAAEAAAB1ZkQIMzgyN"+
"TY2Njk3AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ18b1adfG9WL"), new BasicNameValuePair("request_query", "你是谁"));
		System.out.println(re);
		
		System.out.println(123456);
//		CloseableHttpClient client=getClientByPool();
		
//		HttpGet request=new HttpGet("http://blog.freshz.cn");
//		request.addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
//		HttpEntity entity=null;
//		try {
//			entity = client.execute(request).getEntity();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//			request.abort();
//		} catch (IOException e) {
//			e.printStackTrace();
//			request.abort();
//		}
//		
//		try {
//			System.out.println(EntityUtils.toString(entity));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
