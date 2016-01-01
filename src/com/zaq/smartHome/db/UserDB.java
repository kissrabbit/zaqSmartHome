package com.zaq.smartHome.db;

import java.sql.SQLException;

import com.zaq.smartHome.db.bean.User;
import com.zaq.smartHome.db.bean.YuYin;

/**
 * 用户DB操作
 * @author zaqzaq
 * 2016年1月1日
 *
 */
public class UserDB extends BaseDB{
	private static final String getByUsername="select * from user where username=?";
	
	/**
	 * 按用户名username查询用户失败
	 * @param username
	 * @return
	 */
	public static User getByUsername(String username){
		User user=null;;
		try {
			user = dao.queryForObject(User.class, getByUsername, username);
		} catch (SQLException e) {
			logger.error("按用户名"+username+"查询用户失败", e);
		}
		return user;
		
	}
}
