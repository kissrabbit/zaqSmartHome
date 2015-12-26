package com.zaq.smartHome.controll;

import com.zaq.smartHome.controll.command.GpioCmd;
import com.zaq.smartHome.controll.command.RF315Cmd;
import com.zaq.smartHome.controll.command.RF433Cmd;
import com.zaq.smartHome.controll.command.RedCmd;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.exception.CmdNotFoundException;
import com.zaq.smartHome.util.Constant;
/**
 * 指令工场类
 * @author zaqzaq
 * 2015年12月24日
 *
 */
public class CmdFactory {
	/**
	 * 创建一个带延时命令的指令
	 * @param cmd
	 * @param delay
	 * @return
	 * @throws CmdNotFoundException
	 */
	public static BaseCmd newCommand(Cmd cmd ,Integer delay) throws CmdNotFoundException{
		BaseCmd baseCmd=null;
		switch(cmd.getType()){
			case Constant.TYPE_CMD_RED :
				baseCmd=new RedCmd(cmd,delay);
				break;
			case Constant.TYPE_CMD_RF_315:
				baseCmd=new RF315Cmd(cmd,delay);
				break;
			case Constant.TYPE_CMD_RF_433:
				baseCmd=new RF433Cmd(cmd,delay);
				break;
			case Constant.TYPE_CMD_GPIO:
				baseCmd=new GpioCmd(cmd,delay);
				break;
			default :
				throw new CmdNotFoundException(cmd.getFunction()+"类型"+cmd.getType()+"的指令不存在");
		}
		
		return baseCmd;
	}
	/**
	 * 创建一个默认延时功能的指令
	 * @param cmd
	 * @return
	 * @throws CmdNotFoundException
	 */
	public static BaseCmd newCommand(Cmd cmd) throws CmdNotFoundException{
		BaseCmd baseCmd=null;
		
		switch(cmd.getType()){
			case Constant.TYPE_CMD_RED :
				baseCmd=new RedCmd(cmd);
				break;
			case Constant.TYPE_CMD_RF_315:
				baseCmd=new RF315Cmd(cmd);
				break;
			case Constant.TYPE_CMD_RF_433:
				baseCmd=new RF433Cmd(cmd);
				break;
			case Constant.TYPE_CMD_GPIO:
				baseCmd=new GpioCmd(cmd);
				break;
			default :
				throw new CmdNotFoundException(cmd.getFunction()+"类型"+cmd.getType()+"的指令不存在");
		}
		
		return baseCmd;
	}
}
