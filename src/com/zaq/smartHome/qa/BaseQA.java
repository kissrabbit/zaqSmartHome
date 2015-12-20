package com.zaq.smartHome.qa;

import org.apache.log4j.Logger;

/**
 * 指令处理 问答总接口
 * @author zaqzaq
 * 2015年12月15日
 *
 */
public abstract class BaseQA {
	protected static Logger logger=Logger.getLogger(BaseQA.class);
	/**
	 * 先指令处理
	 * @param question
	 * @return
	 */
	private final String askLocation(String question){
		
		return null;
	}
	public final String ask(String question){
		String ask=askLocation(question);
		if(null==ask){
			ask=askRemote(question);
		}
		
		return ask;
	}
	protected abstract String askRemote(String question);
}
