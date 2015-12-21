package com.zaq.smartHome.controll.command;

import java.util.TimerTask;

import com.zaq.smartHome.controll.CmdDelayTaskCollection;
import com.zaq.smartHome.db.bean.Cmd;

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
	
	public void beforExec(){}
	/**
	 * 指令具体操作
	 */
	public abstract void exec();
	/**
	 * 延时指令具体操作
	 */
	public abstract void execDelay();
	/**
	 * 指令执行模版
	 */
	public final void run(){
		beforExec();
		exec();
		Cmd delayCmd= cmd.getAutoDelayExecCmd();
		if(null!=delayCmd){
			/**
			 * 创建延时任务
			 */
			TimerTask task=new TimerTask() {
				@Override
				public void run() {
					execDelay();
				}
			};
			//添加延时任务到容器
			CmdDelayTaskCollection.addDelayTask(delayCmd.getCode(), delayCmd.getType(), task, getDelay());
		}
		afterExec();
	}
	
	public void afterExec(){
		
	}
}
