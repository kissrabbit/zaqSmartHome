package com.zaq.smartHome.controll.command;

import com.zaq.smartHome.controll.BaseCmd;
import com.zaq.smartHome.db.bean.Cmd;
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
	public RedCmd(Cmd cmd, Integer delay,Long userCreate) {
		super(cmd, delay,userCreate);
	}

	@Override
	protected void exec() {
		// TODO Auto-generated method stub
		
	}
}
