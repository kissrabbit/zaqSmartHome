package com.zaq.smartHome.ws.httpHandle;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zaq.smartHome.db.UserDB;
import com.zaq.smartHome.db.bean.User;
import com.zaq.smartHome.util.Constant;

/**
 * 用户管理
 * @author zaqzaq
 * 2016年1月11日
 *
 */
@RestController  
@RequestMapping("/admin/user")
@Scope("prototype")
public class UserController extends BaseController{

	/**
	 * 用户列表
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("")
	public ModelAndView index(ModelAndView modelAndView){
		
		List<User> users=UserDB.getAll();
		
		modelAndView.addObject("users", users);
		modelAndView.setViewName("admin/user/user");
		return modelAndView;
	}
	/**
	 * 用户详情
	 * @param userId
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/{userId}")
	public ModelAndView showUser(@PathVariable("userId") Long userId,ModelAndView modelAndView){
		User user=UserDB.getById(userId);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("admin/user/userInfo");
		return modelAndView;
	}
	/**
	 * 进入添加用户界面
	 * @param modelAndView
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/addUser")
	public ModelAndView addUser(ModelAndView modelAndView){
		modelAndView.setViewName("admin/user/addUser");
		return modelAndView;
	}
	/**
	 * 重置用户密码
	 * @param username
	 * @param pwd
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/resetPWD")
	public Boolean resetPWD(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "pwd", required = true) String pwd){
		
		return UserDB.resetPWD(username, pwd);
	}
	/**
	 * 永久删除用户
	 * @param username
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/del")
	public Boolean del(@RequestParam(value = "username", required = true) String username){
		
		return UserDB.del(username);
	}
	/**
	 * 保存用户
	 * @param username
	 * @param pwd
	 * @param isManager
	 * @return
	 */
	@RequiresRoles(Constant.ROLE_ADMIN)
	@RequestMapping("/save")
	public Boolean save(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "pwd", required = true) String pwd, @RequestParam(value = "isManager") Boolean isManager){
		Short type=1;
		if(isManager){
			type=0;
		}
		return UserDB.save(username,pwd,type);
	}
}
