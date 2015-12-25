package com.zaq.smartHome.controll.command;

import com.zaq.smartHome.controll.BaseCmd;
import com.zaq.smartHome.db.bean.Cmd;
/**
 * 发送无线315M指令
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public class RF315Cmd extends BaseCmd{

	public RF315Cmd(Cmd cmd) {
		super(cmd);
	}
	public RF315Cmd(Cmd cmd,Integer delay){
		super(cmd, delay);
	}

	@Override
	public void exec() {
		// TODO Auto-generated method stub
		
	}
}
