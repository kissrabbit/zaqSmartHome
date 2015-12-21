package com.zaq.smartHome.db;

import java.sql.SQLException;

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
	private final static String addSql="";
	/**
	 * 按拼音查询指令
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
	
	public void add(YuYin yuYin){
		try {
			BaseDao.getInstance().storeInfo(addSql);
		} catch (SQLException e) {
			logger.error("添加语音缓存库失败", e);
		}
	}
	
}
