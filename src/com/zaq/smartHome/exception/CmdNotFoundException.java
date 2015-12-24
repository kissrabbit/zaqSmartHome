package com.zaq.smartHome.exception;

import org.apache.log4j.Logger;

/**
 *  cmd指令不存在异常
 * @author zaqzaq
 * 2015年12月7日
 *
 */
public class CmdNotFoundException extends CmdException{
	private static final long serialVersionUID = 1L;
	private static Logger logger=Logger.getLogger(CmdNotFoundException.class);
	
	public CmdNotFoundException(String msg){
		super(msg);
		logger.info("cmd指令不存在："+msg, this);
	}
	
    public CmdNotFoundException(String msg,Throwable e) {
        super("cmd指令异常："+msg,e);
		logger.info("cmd指令不存在："+msg, this);

    }
}
