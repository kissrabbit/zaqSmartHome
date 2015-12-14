package com.zaq.smartHome.controll.command;

import com.zaq.smartHome.db.Cmd;

public abstract class BaseCmd {
	private Cmd cmd;//发射的设备码
	private Integer delay;//延時 delay秒后執行关闭操作
	public BaseCmd(Cmd cmd){
		this.cmd=cmd;
	}
	public BaseCmd(Cmd cmd,Integer delay){
		this.cmd=cmd;
		this.delay=delay;
	}
	
	public Cmd getCmd() {
		return cmd;
	}

	public void setCmd(Cmd cmd) {
		this.cmd = cmd;
	}
	/**
	 * 没有设置delay时应用cmd指令的默认延时时间
	 * @return
	 */
	public Integer getDelay() {
		
		if(null==delay){
			delay=cmd.getAutoDelayTime();
		}
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public abstract void exec();
}
