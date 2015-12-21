package com.zaq.smartHome.db.bean;

import java.util.Date;

/**
 * 语音缓存的bean
 * @author zaqzaq
 * 2015年12月21日
 *
 */
public class YuYin {
	private Long id;
	/**
	 * 文件路径
	 */
	private String path;
	/**
	 * 语音文字
	 */
	private String text;
	/**
	 * 创建时间
	 */
	private Date timeCreate;
	/**
	 * 使用次数
	 */
	private Integer useTimes;
	/**
	 * 是否这系统文件
	 */
	private Short isSys;
	/**
	 * 是否删除
	 */
	private Short isDel;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getTimeCreate() {
		return timeCreate;
	}
	public void setTimeCreate(Date timeCreate) {
		this.timeCreate = timeCreate;
	}
	public Integer getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
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
}
