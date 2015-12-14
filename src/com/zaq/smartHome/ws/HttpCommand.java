package com.zaq.smartHome.ws;

import java.util.WeakHashMap;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaq.smartHome.controll.command.BaseCmd;
import com.zaq.smartHome.controll.command.RF315Cmd;
import com.zaq.smartHome.controll.command.RF433Cmd;
import com.zaq.smartHome.controll.command.RedCmd;
import com.zaq.smartHome.db.Cmd;
import com.zaq.smartHome.db.CmdUtil;
import com.zaq.smartHome.db.Constant;

@RestController  
@RequestMapping("/admin/command")
public class HttpCommand {
	private static Logger logger=Logger.getLogger(HttpCommand.class);
	private static WeakHashMap<Long, Cmd> cmdCache=new WeakHashMap<>();
	
	@RequestMapping("/{id}")  
	/**
	 * 执行http请求的指令
	 * @param id 指令的ID
	 * @param delay 延时执行的关闭指令时长
	 * @return
	 */
    public boolean exec(@PathVariable("id") Long id,Integer delay) {  
		Cmd cmd=CmdUtil.getByID(id);
		
		if(null!=cmd){
			cmdCache.put(id, cmd);
		}
		System.out.println(id);
		
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
			default:
				return false;
		}
		baseCmd.exec();
		
        return true;  
    }  
	
}
