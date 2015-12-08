package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

import com.zaq.smartHome.util.AppUtil;

/**
 * http超过了最大错误请求数 异常
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class HttpMaxErrorException extends HttpRequestException{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(HttpMaxErrorException.class);
	
	public HttpMaxErrorException(String url){
		super(url);
		logger.info("请求："+url+ " 失败，超过了最大错误请求数("+AppUtil.getMaxError()+")", this);
	}
	
    public HttpMaxErrorException(String url,Throwable e) {
        super("请求："+url+ " 失败，超过了最大错误请求数("+AppUtil.getMaxError()+")",e);
		logger.info("请求："+url+ " 失败，超过了最大错误请求数("+AppUtil.getMaxError()+")", this);

    }
}
