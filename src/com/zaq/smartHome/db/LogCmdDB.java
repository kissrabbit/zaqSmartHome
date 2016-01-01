package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.Date;

/**
 * 指令日志DB 操作
 * @author zaqzaq
 * 2015年12月21日
 *
 */
public class LogCmdDB extends BaseDB{
	private final static String addSql="INSERT INTO cmd_log (cmdId,timeCreate) VALUES (?,?)";

	/**
	 * 添加一条指令使用的日志
	 * @param cmdId
	 */
	public static void add(Long cmdId){
		try {
			dao.storeInfo(addSql,cmdId,new Date());
		} catch (SQLException e) {
			logger.error("添加指令日志失败", e);
		}
	}
}
