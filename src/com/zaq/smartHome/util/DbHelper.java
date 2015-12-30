package com.zaq.smartHome.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

/**
 * DBUtil的工具类
 * @author zaqzaq
 * 2015年12月30日
 *
 */
public class DbHelper {
	private static Logger logger=Logger.getLogger(DbHelper.class);
	private static boolean hasFULL=false;//数据库连接池是否爆掉
    private static DataSource dataSource;
    private static BeanProcessor processor=new BeanProcessor();
    private DbHelper(){
    }
    /**
     * 初始化数据源
     */
    public  static void init(){
    	newDataSource();
    }
    
    public static <T> T toBean(ResultSet resultSet,Class<T> c) throws SQLException{
    	return processor.toBean(resultSet, c);
    }
    
    public static boolean isHasFULL() {
		return hasFULL;
	}
	/**
     * 
     * @return
     */
    public static QueryRunner getQueryRunner(){
        if(DbHelper.dataSource==null){
        	newDataSource();
        }
        return new QueryRunner(DbHelper.dataSource);
    }
    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getSqlConn(){
    	if(DbHelper.dataSource==null){
    		newDataSource();
    	}
    	
    	try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("数据连接池已爆满", e);
			hasFULL=true;
			return null;
		}
    }
    
    private static synchronized DataSource newDataSource(){
    	 //配置dbcp数据源
    	if(DbHelper.dataSource==null){
       
	        BasicDataSource dbcpDataSource = new BasicDataSource();
	        dbcpDataSource.setUrl(AppUtil.getPropertity("jdbc.url"));
	        dbcpDataSource.setDriverClassName(AppUtil.getPropertity("jdbc.driverClassName"));
	        dbcpDataSource.setUsername(AppUtil.getPropertity("jdbc.username"));
	        dbcpDataSource.setPassword(AppUtil.getPropertity("jdbc.password"));
	        dbcpDataSource.setDefaultAutoCommit(true);
	        dbcpDataSource.setMaxActive(Integer.valueOf(AppUtil.getPropertity("dbcp.MaxActive")));
	        dbcpDataSource.setInitialSize(Integer.valueOf(AppUtil.getPropertity("dbcp.InitialSize")));
	        dbcpDataSource.setMaxIdle(Integer.valueOf(AppUtil.getPropertity("dbcp.MaxIdle")));
	        dbcpDataSource.setMaxWait(Integer.valueOf(AppUtil.getPropertity("dbcp.MaxWait")));
	        dbcpDataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(AppUtil.getPropertity("dbcp.MinEvictableIdleTimeMillis")));
	        dbcpDataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(AppUtil.getPropertity("dbcp.TimeBetweenEvictionRunsMillis")));
	        dataSource=dbcpDataSource;
	        logger.info("初始化数据源...success.......");
    	 }
        return dataSource;
    }
}