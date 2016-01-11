package com.zaq.smartHome.ws.httpHandle;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.smartHome.db.CmdDB;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.util.Constant;
/**
 * 指令管理
 * @author zaqzaq
 * 2016年1月11日
 *
 */
@RestController  
@RequestMapping("/admin/cmd")
@Scope("prototype")
public class CmdController extends BaseController{
	/**
	 * 指令列表
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("")
	public ModelAndView index(ModelAndView modelAndView){
		
		List<Cmd> cmds=CmdDB.getAll();
		
		modelAndView.addObject("cmds", cmds);
		modelAndView.setViewName("admin/cmd/cmd");
		return modelAndView;
	}
	/**
	 * 指令详情
	 * @param cmdId
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/{cmdId}")
	public ModelAndView showCmd(@PathVariable("cmdId") Long cmdId,ModelAndView modelAndView){
		Cmd cmd=CmdDB.getByID(cmdId);
		modelAndView.addObject("cmd", cmd);
		modelAndView.setViewName("admin/cmd/cmdInfo");
		return modelAndView;
	}
	/**
	 * 进入新增指令界面
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/addCmd")
	public ModelAndView addCmd(ModelAndView modelAndView){
		modelAndView.setViewName("admin/cmd/addCmd");
		return modelAndView;
	}
	/**
	 * 假删除指令
	 * @param cmdId
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/del/{cmdId}")
	public Boolean del(@PathVariable("cmdId") Long cmdId){
		
		return CmdDB.del(cmdId);
	}
	/**
	 * 保存指令
	 * @param cmd
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/save")
	public Boolean save(Cmd cmd){
		return CmdDB.save(cmd);
	}
}
