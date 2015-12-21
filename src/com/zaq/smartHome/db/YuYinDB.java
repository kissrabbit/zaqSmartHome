package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.zaq.smartHome.db.bean.YuYin;
import com.zaq.smartHome.util.BaseDao;

/**
 * 语音缓存库
 * @author zaqzaq
 * 2015年12月21日
 *
 */
public class YuYinDB {
	private static Logger logger=Logger.getLogger(YuYinDB.class);
	private final static String getByText="select * from yuyin where text=? and isDel=0";
	private final static String useOne="update yuyin set useTimes=useTimes+1 where text=?";
	private final static String reUse="update yuyin set useTimes=useTimes+1,isDel=0  where text=?";
	private final static String addSql="INSERT INTO yuyin (path, text,timeCreate,useTimes,isSys,isDel) VALUES (?,?,?,1,0,0)";
	/**
	 * 按文本查询指令
	 * @param text 全拼
	 * @return
	 */
	public static YuYin getByText(String text){
		YuYin yuYin=null;;
		try {
			yuYin = BaseDao.getInstance().queryForObject(YuYin.class, getByText, text);
		} catch (SQLException e) {
			logger.error("按文本"+text+"查询语音缓存库失败", e);
		}
		return yuYin;
	}
	
	/**
	 * 使用一次语音+1
	 * @param text
	 */
	public static void useOne(String text){
		try {
			BaseDao.getInstance().update(useOne, text);
		} catch (SQLException e) {
			logger.error("使用一次语音缓存库更新失败", e);
		}
	}
	
	/**
	 * 添加一条语音信息
	 * @param path
	 * @param text
	 */
	public static void add(String text,String path){
		
		YuYin yy= getByText(text);
		
		if(null==yy){
			try {
				BaseDao.getInstance().storeInfo(addSql,path,text,new Date());
			} catch (SQLException e) {
				logger.error("添加语音缓存库失败", e);
			}
		}else{
			try {
				BaseDao.getInstance().update(reUse, text);
			} catch (SQLException e) {
				logger.error("重新使用语音缓存库失败", e);
			}
		}
		
	}

	/**
	 * 获取并使用次数+1
	 * @param text
	 * @return
	 */
	public static YuYin getByTextAndUse(String text) {
		YuYin yy=getByText(text);
		if(yy!=null){
			useOne(text);
		}
		
		return yy;
	}
	
}
