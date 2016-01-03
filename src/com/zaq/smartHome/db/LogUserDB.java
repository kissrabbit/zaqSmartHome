package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.Date;
/**
 * 用户日志操作DB
 * @author zaqzaq
 * 2016年1月3日
 *
 */
public class LogUserDB extends BaseDB{
	private final static String addSql="INSERT INTO user_log (ip,agent,usernameCreate,remark,timeCreate) VALUES (?,?,?,?,?)";

   /**
    * 添加一条用户日志
    * @param ip
    * @param agent
    * @param userCreate
    * @param remark
    */
	public static void add(String ip,String agent,String usernameCreate,String remark){
		try {
			dao.storeInfo(addSql,ip,agent,usernameCreate,remark,new Date());
		} catch (SQLException e) {
			logger.error("添加用户日志失败", e);
		}
	}
}
