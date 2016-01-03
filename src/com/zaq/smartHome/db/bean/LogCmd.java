package com.zaq.smartHome.db.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 指令日志bean
 * @author zaqzaq
 * 2015年12月21日
 *
 */
public class LogCmd implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private User userCreate;//远程操作人
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
	public User getUserCreate() {
		return userCreate;
	}
	public void setUserCreate(User userCreate) {
		this.userCreate = userCreate;
	}
}
