package com.zaq.smartHome.controll.command;

import com.zaq.smartHome.db.Cmd;
/**
 * 发送红外指令
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public class RedCmd extends BaseCmd{

	public RedCmd(Cmd cmd) {
		super(cmd);
	}
	
	public RedCmd(Cmd cmd,Integer delay){
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
