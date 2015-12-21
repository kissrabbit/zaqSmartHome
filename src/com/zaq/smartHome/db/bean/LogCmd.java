package com.zaq.smartHome.db.bean;

import java.util.Date;

/**
 * 指令日志bean
 * @author zaqzaq
 * 2015年12月21日
 *
 */
public class LogCmd {
	private Long id;
	private Cmd cmd; //执行的指令
	private Date timeCreate;//执行时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cmd getCmd() {
		return cmd;
	}
	public void setCmd(Cmd cmd) {
		this.cmd = cmd;
	}
	public Date getTimeCreate() {
		return timeCreate;
	}
	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}
}
