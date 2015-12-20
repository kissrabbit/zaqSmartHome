package com.zaq.smartHome.qa;

/**
 * 指令处理 问答总接口
 * @author zaqzaq
 * 2015年12月15日
 *
 */
public abstract class BaseQA {
	/**
	 * 先指令处理
	 * @param question
	 * @return
	 */
	public final String askLocation(String question){
		
		return null;
	}
	public final String ask(String question){
		String ask=askLocation(question);
		if(null==ask){
			ask=askRemote(question);
		}
		
		return null;
	}
	public abstract String askRemote(String question);
}
