package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

import com.zaq.smartHome.util.AppUtil;

/**
 * http请求 异常
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class HttpRequestException extends Exception{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(HttpRequestException.class);
	public HttpRequestException(String url){
		logger.error("请求："+url+ " 失败", this);
	}
	
    public HttpRequestException(String url,Throwable e) {
        super("请求："+url+ " 失败",e);
		logger.error("请求："+url+ " 失败", this);

    }
}
