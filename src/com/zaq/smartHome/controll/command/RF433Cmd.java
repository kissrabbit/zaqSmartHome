package com.zaq.smartHome.controll.command;

import com.zaq.smartHome.controll.BaseCmd;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.pi4j.rf.RF433;
import com.zaq.smartHome.pi4j.rf.RF433Protocol;
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
	protected void exec() {
		
		RF433.initOrGet().send(Integer.valueOf(getCommand().getCode()),
								RF433Protocol.getById(getCommand().getWirelessProtocol().intValue()));
	}
}
