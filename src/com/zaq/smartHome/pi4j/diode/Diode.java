package com.zaq.smartHome.pi4j.diode;

import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.pi4j.wiringpi.Gpio;
import com.zaq.smartHome.pi4j.BaseGpio;

/**
 * 发光二极管 3.3V
 * @author zaqzaq
 * 2015年12月20日
 *
 */
public class Diode extends BaseGpio{
	protected Diode(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}
	
	protected static Logger logger=Logger.getLogger(Diode.class);
	private static Diode diode; //Singleton 
	private static boolean hasInit=false;//初始化是否成功
	private static final String gpioName="gpio.diode";//配置文件对映的名称
	private static Future<?> diodefuture;//发光二极管工作任務
	//Singleton
	public static Diode init(){
		if(null==diode){
			try {
				diode=new Diode("",gpioName);
				hasInit=true;
			} catch (Exception e) {
				logger.error("初始化发光二极管失败", e);
				hasInit=false;
			}
		}
		return diode;
	}
	
	private void run(long blink){
		stop();
		if(hasInit){
			diodefuture=output.blink(blink);
		}
	}
	
	public void runFast(){
		run(250);
	}
	public void runSlow(){
		run(500);
	}
	
	/**
	 * 快速闪  持续durationMS秒
	 * @param durationMS
	 */
	public void runFastDuration(long durationMS){
		run(250);
		Gpio.delay(durationMS);
		stop();
	}
	/**
	 * 慢速闪  持续durationMS秒
	 * @param durationMS
	 */
	public void runSlowDuration(long durationMS){
		run(250);
		Gpio.delay(durationMS);
		stop();
	}
	public void stop(){
		if(diodefuture!=null){
			diodefuture.cancel(true);
		}
		if(hasInit){
			output.high();
		}
	}
}
