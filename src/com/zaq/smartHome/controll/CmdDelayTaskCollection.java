package com.zaq.smartHome.controll;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 * 延时任务容器
 * @author zaqzaq
 * 2015年12月15日
 *
 */
public class CmdDelayTaskCollection {
	private CmdDelayTaskCollection(){}
	
	private static ScheduledExecutorService executor=Executors.newScheduledThreadPool(5) ;
	
	private static Map<String, ScheduledFuture<?>> delayTasks=new HashMap<>();
	
	/**
	 * 取消或已完成任务
	 * @return
	 */
	public static void delDelayTask(String code,Integer type){
		String key=genKey(code, type);
		if(delayTasks.containsKey(key)){
			delayTasks.get(key) .cancel(true);
		}
	}
	
	/**
	 * 是否存在延时关闭的指令任务
	 * @return
	 */
	public static boolean hasDelayTask(String code,Integer type){
		return delayTasks.containsKey(genKey(code, type));
	}
	/**
	 * 添加一条延时 指令任务
	 * @param code 指令代码
	 * @param type 指令类型
	 * @param task 任务
	 * @param delay 延时多少秒
	 * @return
	 */
	public static void addDelayTask(String code,Integer type,TimerTask task,Integer delay){
		delDelayTask(code, type);
		
		ScheduledFuture<?> scheduledFuture= executor.schedule(task, delay, TimeUnit.SECONDS);
		
		delayTasks.put(genKey(code, type), scheduledFuture);
	}
	/**
	 * key = type+"--"+code
	 */
	private static String genKey(String code,Integer type){
		return type+"--"+code;
	}
}
