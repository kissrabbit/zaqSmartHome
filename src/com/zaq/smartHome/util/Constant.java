package com.zaq.smartHome.util;
/**
 * 常量集
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public interface Constant {
	
	public static final String ASK_NOT_FIND_CMD="没有匹配到可执行的命令"; 
	
	/**
	 * yes
	 */
	public  final short Y=1;
	/**
	 * no
	 */
	public  final short N=0;
	/**
	 * 红外指令
	 */
	public final int TYPE_CMD_RED=1;
	/**
	 * 无线指令 315M
	 */
	public final int TYPE_CMD_RF_315=2;
	/**
	 * 无线指令 433M
	 */
	public final int TYPE_CMD_RF_433=3;
	/**
	 * GPIO 引脚控制
	 */
	public final int TYPE_CMD_GPIO=4;
}
