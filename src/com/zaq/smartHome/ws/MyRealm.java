package com.zaq.smartHome.ws;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.zaq.smartHome.db.UserDB;
import com.zaq.smartHome.db.bean.User;

/**
 * shiro权限操作中心
 * @author zaqzaq
 * 2016年1月1日
 *
 */
public class MyRealm extends AuthorizingRealm {  
    /** 
     * 为当前登录的Subject授予角色和权限 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
//    	 String username = (String) principals.getPrimaryPrincipal();
//         if (StringUtils.isNotEmpty(username)) {
//             SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
//             return authenticationInfo;
//         }
    	//暂时不需要角色分配
    	
    	User user= (User) principals.getPrimaryPrincipal();
    	
    	if(user.getType().shortValue()==User.MANAGER){
    		SimpleAuthorizationInfo authenticationInfo = new SimpleAuthorizationInfo();
    		authenticationInfo.addRole("ROLE_ADMIN");
    		return authenticationInfo;
    	}
    	
        return null;  
    }  
   
       
    /** 
     * 验证当前登录的Subject 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {  
        //获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;  
        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息  
        User user = UserDB.getByUsername(token.getUsername());
		
		if(null!=user){
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user,user.getPassword(), user.getUsername());  
            return authcInfo; 
		}
		
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常  
        return null;  
    }  
}