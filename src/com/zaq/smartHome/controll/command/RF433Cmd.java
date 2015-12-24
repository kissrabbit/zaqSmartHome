package com.zaq.smartHome.controll.command;

import com.zaq.smartHome.controll.BaseCmd;
import com.zaq.smartHome.db.bean.Cmd;
/**
 * 发送无线433M指令
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public class RF433Cmd extends BaseCmd{

	public RF433Cmd(Cmd cmd) {
		super(cmd);
	}
	public RF433Cmd(Cmd cmd,Integer delay){
		super(cmd, delay);
	}
	@Override
	public void exec() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void execDelay() {
		// TODO Auto-generated method stub
		
	}

}
