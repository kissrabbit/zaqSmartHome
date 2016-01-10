package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.zaq.smartHome.db.bean.User;

/**
 * 用户DB操作
 * @author zaqzaq
 * 2016年1月1日
 *
 */
public class UserDB extends BaseDB{
	private static final String getByUsername="select * from user where username=?";
	private static final String getByUserId="select * from user where id=?";
	private static final String delUser="delete from user where username=?";
	private static final String getAll="select * from user";
	private static final String resetPWD="update user set password=? where username=?";
	private static final String updateUser="update user set password=?,type=? where username=?";
	private static final String addUser="insert into user(username,password,type) values(?,?,?)";
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

	/**
	 * 查询所有用户
	 * @return
	 */
	public static List<User> getAll() {
		List<User> users=null;
		
		try {
			users = dao.queryForOList(User.class, getAll);
		} catch (SQLException e) {
			logger.error("查询所有用户失败", e);
		}
		return users;
	}
	/**
	 * 修改密码
	 * @param username
	 * @param pwd
	 * @return
	 */
	public static boolean resetPWD(String username,String pwd){
		
		if(StringUtils.isEmpty(pwd)){
			return false;
		}
		boolean retVal=true;
		try {
			dao.update(resetPWD,DigestUtils.md5Hex(pwd), username);
		} catch (SQLException e) {
			logger.error("修改用户:"+username+"的密码失败", e);
			retVal=false;
		}
		return retVal;
	}

	/**
	 * 按ID查询
	 * @param userId
	 * @return
	 */
	public static User getById(Long userId) {
		User user=null;;
		try {
			user = dao.queryForObject(User.class, getByUserId, userId);
		} catch (SQLException e) {
			logger.error("按用户ID"+userId+"查询用户失败", e);
		}
		return user;
	}

	/**
	 * 删除用户
	 * @param username
	 * @return
	 */
	public static Boolean del(String username) {
		boolean retVal=true;
		if("admin".equals(username)){
			retVal=false;
		}else{
			try {
				dao.update(delUser, username);
			} catch (SQLException e) {
				retVal=false;
				logger.error("删除用户"+username+"失败", e);
			}
		}
		
		return retVal;
	}

	/**
	 * 保存用户
	 * @param username
	 * @param pwd
	 * @param type
	 * @return
	 */
	public static Boolean save(String username, String pwd, Short type) {
		boolean retVal=true;
		try {
			if(null!=getByUsername(username)){
				dao.update(updateUser, DigestUtils.md5Hex(pwd),type,username);
			}else{
				dao.storeInfo(addUser, username,DigestUtils.md5Hex(pwd),type);
			}
		} catch (SQLException e) {
			logger.error("新增用户"+username+"失败", e);
			retVal=false;
		}
		
		return retVal;
	}
}
