package com.zaq.smartHome.pi4j.bodyInfrared;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.zaq.smartHome.pi4j.BaseGpio;
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
	private static BodyInfrared bodyInfrared; //Singleton 
	private static final String gpioName="gpio.bodyInfrared";//配置文件对映的名称
	//Singleton
	public static BodyInfrared initOrGet(){
		if(null==bodyInfrared){
			try {
				bodyInfrared=new BodyInfrared(gpioName,"");
				logger.info("初始人体红外感应器成功"+bodyInfrared.input.getName());
				executor=Executors.newSingleThreadScheduledExecutor();
			} catch (Exception e) {
				logger.error("初始人体红外感应器失败", e);
				bodyInfrared.hasInit=false;
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
		
		if(!Csb.initOrGet().isRun()){
			Csb.initOrGet().run();
			Diode.initOrGet().runFastDuration(2000);
		}
		
		//检测不到人时延迟30秒关闭超声波
		taskFuture=executor.schedule(new Runnable() {
			@Override
			public void run() {
				Csb.initOrGet().stop();
//				Been.init().runFastDuration(2000); 注掉，如果轰鸣会被红外检测到有人。。。。无语
			}
		}, 30, TimeUnit.SECONDS);
		
	}
	/**
	 * 人走开了
	 */
	private void off(){
	}
}
