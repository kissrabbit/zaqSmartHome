package com.zaq.smartHome.pi4j.bodyInfrared;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.zaq.smartHome.pi4j.BaseGpio;
import com.zaq.smartHome.pi4j.been.Been;
import com.zaq.smartHome.pi4j.csb.Csb;
import com.zaq.smartHome.pi4j.diode.Diode;

/**
 * 人体红外感应 HC-SR501 人体模块 5V
 * 这个比软坑，需要人动才能感映得到
 * @author zaqzaq
 * 2015年12月20日
 *
 */
public class BodyInfrared extends BaseGpio{
	protected BodyInfrared(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}
	
	private static ScheduledExecutorService executor;
	private static ScheduledFuture<?> taskFuture;
	private static Logger logger=Logger.getLogger(BodyInfrared.class);
	private static BodyInfrared bodyInfrared; //Singleton 
	private static boolean hasInit=false;//初始化是否成功
	private static final String gpioName="gpio.bodyInfrared";//配置文件对映的名称
	//Singleton
	public static BodyInfrared init(){
		if(null==bodyInfrared){
			try {
				bodyInfrared=new BodyInfrared(gpioName,"");
				logger.debug("初始人体红外感应器成功"+bodyInfrared.input.getName());
				hasInit=true;
				executor=Executors.newSingleThreadScheduledExecutor();
			} catch (Exception e) {
				logger.error("初始人体红外感应器失败", e);
				hasInit=false;
			}
		}
		return bodyInfrared;
	}
	
	/**
	 * 启动人体红外监听
	 */
	public void listener(){
		if(hasInit){
			input.addListener(new GpioPinListenerDigital() {

				@Override
				public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
					if(event.getState().isHigh()){
						on();
					}else{
						off();
					}
				}
				
			} );
		}
		
	}
	/**
	 * 检测到有人
	 */
	private void on(){
		logger.debug("红外检测到有人");
		if(null!=taskFuture){
			taskFuture.cancel(true);
		}
		
		if(!Csb.init().isRun()){
			Csb.init().run();
			Diode.init().runFastDuration(2000);
		}
		
		//延迟1分钟关闭超声波
		taskFuture=executor.schedule(new Runnable() {
			@Override
			public void run() {
				Csb.init().stop();
//				Been.init().runFastDuration(2000); 注掉，如果轰鸣会被红外检测到有人。。。。无语
			}
		}, 1, TimeUnit.MINUTES);
		
	}
	/**
	 * 人走开了
	 */
	private void off(){
	}
}
