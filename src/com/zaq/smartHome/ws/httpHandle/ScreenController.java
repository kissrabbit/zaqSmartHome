package com.zaq.smartHome.ws.httpHandle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统默认跳转
 * @author zaqzaq
 * 2016年1月2日
 *
 */
@RestController  
@RequestMapping("/screen")
public class ScreenController extends BaseController{
	
	/**
	 * 未授权则跳转到登陆页面
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/noAuth")
	public ModelAndView noAuth(ModelAndView modelAndView,HttpServletRequest request){
		
		if("auth".equals(request.getSession().getAttribute("isAuth"))){
			modelAndView.setViewName("redirect:/resources/403.html");
		}else{
			modelAndView.setViewName("redirect:/");
		}
		
		return modelAndView;
	}
	/**
	 * ERROE错误跳转
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/error")
	public ModelAndView error(ModelAndView modelAndView){
		modelAndView.setViewName("redirect:/resources/500.html");
		return modelAndView;
	}
	
	/**
	 * 404页面
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/notFound")
	public ModelAndView notFound(ModelAndView modelAndView){
		modelAndView.setViewName("redirect:/resources/404.html");
		return modelAndView;
	}
}
