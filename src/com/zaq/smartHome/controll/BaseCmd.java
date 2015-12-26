package com.zaq.smartHome.controll;

import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.zaq.smartHome.db.bean.Cmd;

public abstract class BaseCmd {
	private static Logger logger=Logger.getLogger(BaseCmd.class);
	private Cmd command;//发射的设备码
	private Integer delay;//延時 delay秒后執行关闭操作
	public BaseCmd(Cmd cmd){
		this.command=cmd;
	}
	public BaseCmd(Cmd cmd,Integer delay){
		this.command=cmd;
		this.delay=delay;
	}
	
	public Cmd getCommand() {
		return command;
	}

	/**
	 * 没有设置delay时应用cmd指令的默认延时时间
	 * @return
	 */
	public Integer getDelay() {
		
		if(null==delay||0==delay.intValue()){
			delay=command.getAutoDelayTime();
		}
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	/**
	 * 指令执行前的操作
	 */
	public void beforExec(){
		logger.info("开始执行命令:"+command.getCmd());
	}
	/**
	 * 指令具体操作
	 */
	protected abstract void exec();
	
	/**
	 * 延时指令具体操作
	 */
	public final void execDelay(){
		CmdFactory.newCommand(command.getAutoDelayExecCmd()).run();
	};
	/**
	 * 指令执行模版
	 */
	public final void run(){
		beforExec();
		exec();
		//是否延时执行延时指令
		if(null!=getDelay()&&getDelay()>0){
			Cmd delayCmd= command.getAutoDelayExecCmd();
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
				logger.info("将延时"+getDelay()+"秒执行命令："+delayCmd.getCmd());
			}
		}
	
		afterExec();
	}
	
	/**
	 * 指令执行后的操作
	 */
	public void afterExec(){
		logger.info("执行完成命令:"+command.getCmd());
	}
}
