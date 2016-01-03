package com.zaq.smartHome.ws.httpHandle;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zaq.smartHome.controll.CmdFactory;
import com.zaq.smartHome.db.CmdDB;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.db.bean.User;

/**
 * 指令的http请求
 * @author zaqzaq
 * 2015年12月29日
 *
 */
@RestController  
@RequestMapping("/command")
@Scope("prototype")
public class CommandController extends BaseController{
	
	/**
	 * 执行http请求的指令
	 * @param id 指令的ID
	 * @param delay 延时执行的关闭指令时长
	 * @return
	 */
	@RequestMapping("/{id}/{delay}") 
	@RequiresAuthentication
    public boolean exec(@PathVariable("id") Long id,@PathVariable("delay")Integer delay) {  
		Cmd cmd=CmdDB.getByID(id);
		
		if(null==cmd){
			logger.error("指令["+id+"]不存在");
			return false;
		}
		User user= (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		try {
			CmdFactory.newCommand(cmd, delay,user.getId()).run();
		} catch (Exception e) {
			logger.error("执行指令["+id+"]异常", e);
			return false;
		}
		
        return true;  
    }  
	
}
