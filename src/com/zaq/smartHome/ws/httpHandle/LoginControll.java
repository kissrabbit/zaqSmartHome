package com.zaq.smartHome.ws.httpHandle;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController  
public class LoginControll {
	@RequestMapping("/")
	public ModelAndView login(){
		ModelAndView modelAndView=new ModelAndView();
		
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return "aassa枯萎b";
	}
}
