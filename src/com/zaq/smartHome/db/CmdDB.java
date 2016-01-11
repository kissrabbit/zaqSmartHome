package com.zaq.smartHome.db;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.WeakHashMap;

import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.util.PinyingUtil;

/**
 * 对指令的  DB操作
 * @author zaqzaq
 * 2015年12月14日
 *
 */
public class CmdDB  extends BaseDB{
	private static final String getByPY="select * from cmd where py=? and isDel=0";
	private static final String getByID="select * from cmd where id=? and isDel=0";
	private static final String countCmd="select count(0) from cmd where isDel=?";
	
	private static final String delCmd="update cmd set isDel=1 where id=?";
	private static final String getAll="select * from cmd  where isDel=0";
	private static final String updateCmd="update cmd set function=?,cmd=?,py=?,type=?,wirelessProtocol=?,code=?,autoDelayTime=?,autoDelayExecId=? where id=?";
	private static final String addCmd="insert into cmd(function,cmd,py,type,code,wirelessProtocol,isSys,isDel,timeCreate,autoDelayTime,autoDelayExecId) values(?,?,?,?,?,?,1,0,?,?,?)";
	
	/**
	 * 缓存下指令
	 */
	private static WeakHashMap<String, Cmd> cmdCache=new WeakHashMap<>();
	
	/**
	 * 统计指令数
	 * @param isDel
	 * @return
	 */
	public static int countCmd(Short isDel){
		int count=0;
		try {
			count=dao.count(countCmd,isDel);
		} catch (SQLException e) {
			logger.error("统计指令数失败isDel:"+isDel, e);
		}
		return count;
	}
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
			cmd = dao.queryForObject(Cmd.class, getByPY, py);
			if(null!=cmd){
				cmdCache.put(py, cmd);
				cmdCache.put(cmd.getId().toString(), cmd);
				if(null!=cmd.getAutoDelayExecId()){
					cmd.setAutoDelayExecCmd(dao.queryForObject(Cmd.class, getByID, cmd.getAutoDelayExecId()));

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
			cmd = dao.queryForObject(Cmd.class, getByID, id);
			
			if(null!=cmd){
				cmdCache.put(id.toString(), cmd);
				cmdCache.put(cmd.getPy(), cmd);
				if(null!=cmd.getAutoDelayExecId()){
					cmd.setAutoDelayExecCmd(dao.queryForObject(Cmd.class, getByID, cmd.getAutoDelayExecId()));

				}
			}
			
		} catch (SQLException e) {
			logger.error("按ID"+id+"查询指令失败", e);
		}
		return cmd;
	}
	/**
	 * 查询所有指令
	 * @return
	 */
	public static List<Cmd> getAll() {
		List<Cmd> cmds=null;
		
		try {
			cmds = dao.queryForOList(Cmd.class, getAll);
		} catch (SQLException e) {
			logger.error("查询所有指令失败", e);
		}
		return cmds;
	}
	/**
	 * 删除指令
	 * @param cmdId
	 * @return
	 */
	public static Boolean del(Long cmdId) {
		boolean retVal=true;
		try {
			dao.update(delCmd, cmdId);
		} catch (SQLException e) {
			retVal=false;
			logger.error("删除指令"+cmdId+"失败", e);
		}
		
		return retVal;
	}
	
	/**
	 * 保存指令
	 * @param cmd
	 * @return
	 */
	public static Boolean save(Cmd cmd) {
		if(null==cmd){
			return false;
		}
		boolean retVal=true;
		if(null==cmd.getId()){
			try {
//				addCmd="insert into cmd(function,cmd,py,type,code,wirelessProtocol,isSys,isDel,timeCreate,autoDelayTime,autoDelayExecId) values(?,?,?,?,?,?,1,0,?,?,?)";
				dao.storeInfo(addCmd, cmd.getFunction(),cmd.getCmd(),PinyingUtil.hanziToPinyinWithAz(cmd.getCmd()),
						cmd.getType(),cmd.getCode(),cmd.getWirelessProtocol(),new Date(),cmd.getAutoDelayTime(),cmd.getAutoDelayExecCmd());
			} catch (SQLException e) {
				retVal=false;
				logger.error("新增指令"+cmd.getCmd()+"失败", e);
			}
		}else{
			try {
//				updateCmd="update cmd set function=?,cmd=?,py=?,type=?,wirelessProtocol=?,code=?,autoDelayTime=?,autoDelayExecId=? where id=?";
				dao.update(updateCmd, cmd.getFunction(),cmd.getCmd(),PinyingUtil.hanziToPinyinWithAz(cmd.getCmd()),
						cmd.getType(),cmd.getWirelessProtocol(),cmd.getCode(),cmd.getAutoDelayTime(),cmd.getAutoDelayExecId(),cmd.getId());
			} catch (SQLException e) {
				retVal=false;
				logger.error("更新指令"+cmd.getCmd()+"失败", e);
			}
		}
		
		return retVal;
	}
	
}
