package com.zaq.smartHome.db;
/**
 * 常量集
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public interface Constant {
	/**
	 * yes
	 */
	public  final int Y=1;
	/**
	 * no
	 */
	public  final int N=0;
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
}
