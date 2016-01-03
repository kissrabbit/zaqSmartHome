package com.zaq.smartHome.ws.httpHandle;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.smartHome.db.CmdDB;
import com.zaq.smartHome.db.bean.User;
import com.zaq.smartHome.util.Constant;

/**
 * 后台管理操作Controller
 * @author zaqzaq
 * 2016年1月3日
 *
 */
@RestController  
@RequestMapping("/admin")
@Scope("prototype")
public class ManageController extends BaseController{
	
	/**
	 * 后台管理的首页
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequiresAuthentication
	@RequestMapping("")
	public ModelAndView index(ModelAndView modelAndView){
		User user= (User) SecurityUtils.getSubject().getPrincipal();
		
		modelAndView.addObject("username", user.getUsername());
		
		int used=CmdDB.countCmd(Constant.N);
		int del=CmdDB.countCmd(Constant.Y);
		modelAndView.addObject("total", used+del);
		modelAndView.addObject("used", used);
		modelAndView.addObject("del",del);
		
		
		modelAndView.setViewName("admin/index");
		return modelAndView;
	}
}
