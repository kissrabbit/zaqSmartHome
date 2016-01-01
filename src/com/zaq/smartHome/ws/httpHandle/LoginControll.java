package com.zaq.smartHome.ws.httpHandle;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登陆请求处理
 * @author zaqzaq
 * 2015年12月30日
 *
 */
@RestController  
public class LoginControll {
	@RequestMapping("/")
	public ModelAndView login(){
		ModelAndView modelAndView=new ModelAndView();
		
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping("/test")
//	@RequestMapping(value="/test",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	@RequiresUser
	public String test(){
		return "freshz*果 果*";
	}
}
