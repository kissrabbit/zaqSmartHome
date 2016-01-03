package com.zaq.smartHome.db.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户登陆登出日志
 * @author zaqzaq
 * 2016年1月3日
 *
 */
public class LogUser implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	private String usernameCreate;//创建人
	private Date timeCreate;//创建时间
	private String ip;//ip地址
	private String agent;
	private String remark;//备注
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsernameCreate() {
		return usernameCreate;
	}
	public void setUsernameCreate(String usernameCreate) {
		this.usernameCreate = usernameCreate;
	}
	public Date getTimeCreate() {
		return timeCreate;
	}
	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
