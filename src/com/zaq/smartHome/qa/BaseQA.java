package com.zaq.smartHome.qa;

import org.apache.log4j.Logger;

import com.zaq.smartHome.db.CmdDB;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.util.Constant;
import com.zaq.smartHome.util.PinyingUtil;

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
		
		//TODO 从中解析出延时的分钟数
		
		Cmd cmd=CmdDB.getByPY(PinyingUtil.hanziToPinyinWithAz(question));
		
		if(null!=cmd){
			//TODO 执行命令
			
			return null;
		}else{
			return Constant.ASK_NOT_FIND_CMD;
		}
		
		
	}
	public final String ask(String question){
		String ask=askLocation(question);
		if(Constant.ASK_NOT_FIND_CMD.equals(ask)){
			ask=askRemote(question);
		}
		
		return ask;
	}
	protected abstract String askRemote(String question);
}
