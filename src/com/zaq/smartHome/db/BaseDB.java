package com.zaq.smartHome.db;

import org.apache.log4j.Logger;

import com.zaq.smartHome.util.BaseDao;

/**
 * 数据库操作的基类
 * @author zaqzaq
 * 2016年1月1日
 *
 */
public abstract class BaseDB {
	protected static Logger logger=Logger.getLogger(BaseDB.class);
	protected static BaseDao dao=BaseDao.getInstance();
}
