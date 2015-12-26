package com.zaq.smartHome.qa;

import org.apache.log4j.Logger;

import com.zaq.smartHome.controll.CmdFactory;
import com.zaq.smartHome.db.CmdDB;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.qa.ParseUtil.TextDelay;
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
		Cmd cmd=CmdDB.getByPY(PinyingUtil.hanziToPinyinWithAz(question));
		
		if(null!=cmd){
			//执行无延时指令
			CmdFactory.newCommand(cmd).run();
			return null;
		}
		
		//解析延时指令
		TextDelay textDelay  = ParseUtil.text2Cmd(question);
		//非控制指令 ，返回进行QA机器回答
		if(null==textDelay){
			return Constant.ASK_NOT_FIND_CMD;
		}
		
		cmd=CmdDB.getByPY(PinyingUtil.hanziToPinyinWithAz(textDelay.cmd));
		
		if(null!=cmd){
			//执行有延时指令
			CmdFactory.newCommand(cmd,textDelay.delay).run();
			
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
