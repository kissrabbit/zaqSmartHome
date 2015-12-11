package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

/**
 * 系统 异常
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class SystemException extends Exception{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(SystemException.class);
	
    public SystemException(String msg,Throwable e) {
        super("系统异常："+msg,e);
		logger.info("系统异常："+msg, this);

    }
}
