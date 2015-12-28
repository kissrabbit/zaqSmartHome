package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.zaq.smartHome.db.bean.YuYin;
import com.zaq.smartHome.util.BaseDao;

/**
 * 指令日志DB 操作
 * @author zaqzaq
 * 2015年12月21日
 *
 */
public class LogCmdDB {
	private static Logger logger=Logger.getLogger(LogCmdDB.class);
	private final static String addSql="INSERT INTO cmd_log (cmdId,timeCreate) VALUES (?,?)";

	/**
	 * 添加一条指令使用的日志
	 * @param cmdId
	 */
	public static void add(Long cmdId){
		try {
			BaseDao.getInstance().storeInfo(addSql,cmdId,new Date());
		} catch (SQLException e) {
			logger.error("添加指令日志失败", e);
		}
	}
}
