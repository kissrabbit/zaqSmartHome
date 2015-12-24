package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

/**
 * GPIO引脚 异常
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class GpioException extends Exception{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(GpioException.class);
	
	public GpioException(String msg){
		super(msg);
		logger.error("gpio异常："+msg, this);
	}
	
    public GpioException(String msg,Throwable e) {
        super("gpio异常："+msg,e);
		logger.error("gpio异常："+msg, this);

    }
}
