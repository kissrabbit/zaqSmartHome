package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

/**
 * 设备资源正在占用中
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class ResRuningException extends Exception{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(ResRuningException.class);
	
	public ResRuningException(String resName){
		super(resName);
		logger.info(resName+"设备正在占用中", this);
	}
	
}
