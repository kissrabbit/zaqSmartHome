package com.zaq.smartHome.pi4j.been;

import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pi4j.wiringpi.Gpio;
import com.zaq.smartHome.pi4j.BaseGpio;
import com.zaq.smartHome.util.ThreadPool;

/**
 * 轰鸣器 3.3V
 * @author zaqzaq
 * 2015年12月20日
 *
 */
public class Been extends BaseGpio{
	protected Been(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}
	
	protected static Logger logger=Logger.getLogger(Been.class);
	private static Been been; //Singleton 
	private static boolean hasInit=false;//初始化是否成功
	private static final String gpioName="gpio.been";//配置文件对映的名称
	private static Future<?> beenfuture;//轰鸣器工作任務
	//Singleton
	public static Been init(){
		if(null==been){
			try {
				been=new Been("",gpioName);
				hasInit=true;
			} catch (Exception e) {
				logger.error("初始化轰鸣器失败", e);
				hasInit=false;
			}
		}
		return been;
	}
	
	private void run(long blink){
		stop();
		if(hasInit){
			beenfuture=output.blink(blink);
		}
	}
	/**
	 * 快速鸣
	 */
	public void runFast(){
		run(250);
	}
	/**
	 * 慢速鸣
	 */
	public void runSlow(){
		run(500);
	}
	/**
	 * 快速鸣  持续durationMS秒
	 * @param durationMS
	 */
	public void runFastDuration(final long durationMS){

		ThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				Been.this.run(250);
				Gpio.delay(durationMS);
				Been.this.stop();				
			}
		});
	
	}
	/**
	 * 慢速鸣  持续durationMS秒
	 * @param durationMS
	 */
	public void runSlowDuration(final long durationMS){

		ThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				Been.this.run(500);
				Gpio.delay(durationMS);
				Been.this.stop();				
			}
		});
	
	}
	
	public synchronized void stop(){
		if(beenfuture!=null){
			beenfuture.cancel(true);
		}
		if(hasInit){
			output.high();
		}
	}
}
