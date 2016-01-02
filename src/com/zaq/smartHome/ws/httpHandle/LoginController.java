package com.zaq.smartHome.ws.httpHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.smartHome.db.bean.User;

/**
 * 登陆请求处理
 * @author zaqzaq
 * 2015年12月30日
 *
 */
@RestController
@Scope("prototype")
public class LoginController extends BaseController{
	
	@RequestMapping("/")
	public ModelAndView loginView(ModelAndView modelAndView){
		if (SecurityUtils.getSubject().isAuthenticated()) {
			//通过认证的进入主界面
			modelAndView.setViewName("redirect:/index");
		}else{
			//时入登陆界面
			if(!modelAndView.getModel().containsKey("message_login")){
				modelAndView.addObject("message_login", "");
			}
			modelAndView.setViewName("login");
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView modelAndView,@RequestParam(value = "userName", required = true) String username, @RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "rememberMe", required = false) String rememberMe, HttpServletResponse response,HttpServletRequest request){
		if (SecurityUtils.getSubject().isAuthenticated()) {
			//已通过认证的进入主界面
			modelAndView.setViewName("redirect:/index");
		}else{
			//登陆验证
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			logger.debug("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
			// 获取当前的Subject
			Subject currentUser = SecurityUtils.getSubject();
			try {
				// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
				// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
				// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
				logger.debug("对用户[" + username + "]进行登录验证..验证开始");
				currentUser.login(token);
				logger.debug("对用户[" + username + "]进行登录验证..验证通过");
			} catch (UnknownAccountException uae) {
				logger.debug("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
				modelAndView.addObject("message_login", "未知账户");
			} catch (IncorrectCredentialsException ice) {
				logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
				modelAndView.addObject("message_login", "密码不正确");
			} catch (LockedAccountException lae) {
				logger.debug("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
				modelAndView.addObject("message_login", "账户已锁定");
			} catch (ExcessiveAttemptsException eae) {
				logger.debug("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
				modelAndView.addObject("message_login", "用户名或密码错误次数过多");
			} catch (AuthenticationException ae) {
				// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
				logger.debug("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
				ae.printStackTrace();
				modelAndView.addObject("message_login", "用户名或密码不正确");
			}
			
			// 验证是否登录成功
			if (currentUser.isAuthenticated()) {
				//防止shiro在request再开线程找不到SecurityManager引起的BUG
				request.getSession().setAttribute("isAuth", "auth");
				logger.info("用户[" + username + "]登录认证通过");
				modelAndView.setViewName("redirect:/index");
			} else {
				token.clear();
				modelAndView.setViewName("login");
			}
			
		}
		
		return modelAndView;
	}
	
	@RequestMapping("/index")
	@RequiresAuthentication
	public ModelAndView index(ModelAndView modelAndView){
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(ModelAndView modelAndView,HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			logger.info("用户" + ((User)subject.getPrincipal()).getUsername() + "退出登录");
			subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
			request.getSession().invalidate();
		}
		modelAndView.setViewName("redirect:/");
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
