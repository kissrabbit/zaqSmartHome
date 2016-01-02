package com.zaq.smartHome.ws.httpHandle;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController  
@RequestMapping("/admin")
@Scope("prototype")
public class ManageController extends BaseController{
	
	@RequiresRoles("ROLE_ADMIN")
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView modelAndView){
		modelAndView.setViewName("index");
		return modelAndView;
	}
}
