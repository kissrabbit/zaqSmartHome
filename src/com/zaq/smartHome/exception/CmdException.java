package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

/**
 *  cmd指令异常
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class CmdException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(CmdException.class);
	
	public CmdException(String msg){
		super(msg);
		logger.info("cmd指令异常："+msg, this);
	}
	
    public CmdException(String msg,Throwable e) {
        super("cmd指令异常："+msg,e);
		logger.info("cmd指令异常："+msg, this);

    }
}
