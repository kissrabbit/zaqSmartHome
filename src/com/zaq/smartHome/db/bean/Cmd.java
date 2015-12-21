package com.zaq.smartHome.db.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 指令实体bean
 * @author zaqzaq
 * 2015年12月13日
 *
 */
public class Cmd implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String  function;//功能描述
	private String  cmd;//文本字令
	private String  py;//拼音 ：以拼音做查询更符合普通话不太标准和亲们
	private Integer  type;//发送类型 1：红外 2：无线 
	private String  code;//发送给设备的指令
	private Short  isSys;//是否为系统的指令 1：是 0：否
	private Short  isDel;//是否删除 1:是 0：否
	private Date  timeCreate;//创建时间
	private Integer autoDelayTime;//默认指定的延时（单位s秒）
	private Long autoDelayExecId;//延时执行的指令
	
	private Cmd autoDelayExecCmd;//延时执行的指令 对象
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getPy() {
		return py;
	}
	public void setPy(String py) {
		this.py = py;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Short getIsSys() {
		return isSys;
	}
	public void setIsSys(Short isSys) {
		this.isSys = isSys;
	}
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	public Date getTimeCreate() {
		return timeCreate;
	}
	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}
	public Integer getAutoDelayTime() {
		return autoDelayTime;
	}
	public void setAutoDelayTime(Integer autoDelayTime) {
		this.autoDelayTime = autoDelayTime;
	}
	public Long getAutoDelayExecId() {
		return autoDelayExecId;
	}
	public void setAutoDelayExecId(Long autoDelayExecId) {
		this.autoDelayExecId = autoDelayExecId;
	}
	public Cmd getAutoDelayExecCmd() {
		return autoDelayExecCmd;
	}
	public void setAutoDelayExecCmd(Cmd autoDelayExecCmd) {
		this.autoDelayExecCmd = autoDelayExecCmd;
	}
}
