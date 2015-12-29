package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;

import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.util.BaseDao;

/**
 * 对指令的  DB操作
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public class CmdDB {
	private static Logger logger=Logger.getLogger(CmdDB.class);
	private static String getByPY="select * from cmd where py=? and isDel=0";
	private static String getByID="select * from cmd where id=? and isDel=0";
	/**
	 * 缓存下指令
	 */
	private static WeakHashMap<String, Cmd> cmdCache=new WeakHashMap<>();
	/**
	 * 按拼音查询指令
	 * @param py 全拼
	 * @return
	 */
	public static Cmd getByPY(String py){
		if(cmdCache.containsKey(py)){
			return  cmdCache.get(py);
		}
		Cmd cmd=null;;
		try {
			cmd = BaseDao.getInstance().queryForObject(Cmd.class, getByPY, py);
			if(null!=cmd){
				cmdCache.put(py, cmd);
				cmdCache.put(cmd.getId().toString(), cmd);
				if(null!=cmd.getAutoDelayExecId()){
					cmd.setAutoDelayExecCmd(BaseDao.getInstance().queryForObject(Cmd.class, getByID, cmd.getAutoDelayExecId()));

				}
			}
		} catch (SQLException e) {
			logger.error("按拼音"+py+"查询指令失败", e);
		}
		return cmd;
	}
	
	/**
	 * 按ID查询指令
	 * @param ID id
	 * @return
	 */
	public static Cmd getByID(Long id){
		
		if(cmdCache.containsKey(id.toString())){
			return  cmdCache.get(id.toString());
		}
		Cmd cmd=null;
		try {
			cmd = BaseDao.getInstance().queryForObject(Cmd.class, getByID, id);
			
			if(null!=cmd){
				cmdCache.put(id.toString(), cmd);
				cmdCache.put(cmd.getPy(), cmd);
				if(null!=cmd.getAutoDelayExecId()){
					cmd.setAutoDelayExecCmd(BaseDao.getInstance().queryForObject(Cmd.class, getByID, cmd.getAutoDelayExecId()));

				}
			}
			
		} catch (SQLException e) {
			logger.error("按ID"+id+"查询指令失败", e);
		}
		return cmd;
	}
}
