package com.zaq.smartHome.pi4j.csb;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

/**
 * 超声波HC-SR04 模块控制 二级管及轰鸣器
 * @author zaqzaq
 * 2015年12月5日
 *
 */
public class Csb {
	static GpioPinDigitalOutput twoG;// 2极管口
	static  GpioPinDigitalOutput been;// 轰鸣器口
	static GpioPinDigitalOutput so;// 超声音波 发出口
	static GpioPinDigitalInput si;// 超声音波 接收口
	static int STAT=0;//轰鸣器叫 2极管闪烁 工作状态 0;停止 1:慢 2：快
	
	static Future twoGf;//2极管工作任務
	static Future beenf;//轰鸣器工作任務
	
	static Executor executor;
	
	//轰鸣器叫 2极管闪烁 距离越近闪烁越快
	private static void beepAndLightFast(){
		if(twoGf!=null){
			twoGf.cancel(true);
		}
		if(beenf!=null){
			beenf.cancel(true);
		}
		twoGf=twoG.blink(250);
		beenf=been.blink(250);
		System.out.println("fast");
	}
	//轰鸣器叫 2极管闪烁 距离越近闪烁越慢
	private static void beepAndLightSlow(){
		if(twoGf!=null){
			twoGf.cancel(true);
		}
		if(beenf!=null){
			beenf.cancel(true);
		}
		twoGf=twoG.blink(500);
		beenf=been.blink(500);
		System.out.println("slow");
	}
	//轰鸣器叫 2极管闪烁 停止工作
	private static void stop(){
		if(twoGf!=null){
			twoGf.cancel(true);
		}
		if(beenf!=null){
			beenf.cancel(true);
		}
		twoG.high();
		been.high();
		System.out.println("stop");
	}
	/**
	 * 檢測距離
	 * @return
	 * @throws InterruptedException
	 */
	private static float  checkdist() throws InterruptedException{
//		System.out.println("send s start...");
        //发出触发信号
        so.high();
        //保持10us以上（这里选择15us）
        Thread.sleep(0, 15000);
        so.low();
        //妈的太近了，没有这句话，你就等着下面的死循环吧-_-!
        FutureTask<Void> future=new FutureTask<Void>(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				 while (si.isLow()){
			        	//不用处理，，，设备正在发送超声波中。。。。
//			        	System.out.println("Do not handle, and the device is transmitting ultrasonic waves....");
			        }
				return null;
			}
		});
        executor.execute(future);
        try {
			future.get(200, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			System.out.println("Do not handle,too close....");
        	return 0;
		}
        //发送完毕 发现高电平时开时计时
//        System.out.println("Sent to the end of the discovery of high power usually open time");
        long t1 = System.nanoTime();
        while (si.isHigh()){
        	//不用处理，，，设备正在接收超声波。。。。
//        	System.out.println("Do not handle, and the device is receiving ultrasound....");
        }
        //接收到回应 高电平结束停止计时
//        System.out.println("Received in response to the high end stop time");
        long t2 = System.nanoTime();
        //返回距离，单位为米
        return ((float)(t2-t1)/1000000000)*340/2;
	}
    public static void main(String[] args) throws InterruptedException {
    	executor=Executors.newSingleThreadExecutor();
    	
        final GpioController gpio = GpioFactory.getInstance();
        // 2极管口
        twoG = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
        // 轰鸣器口
        been = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);

        // 超声音波 发出口
        so = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24);
        //  接收口
        si = gpio.provisionDigitalInputPin(RaspiPin.GPIO_25, PinPullResistance.PULL_DOWN);
        //自动释放gpio口 or user aborts (CTRL-C)
        twoG.setShutdownOptions(true);
        been.setShutdownOptions(true);
        so.setShutdownOptions(true);
        si.setShutdownOptions(true);
       /* //红外 待接入
        final GpioPinDigitalInput red = gpio.provisionDigitalInputPin(RaspiPin.GPIO_18, PinPullResistance.PULL_DOWN);

        red.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // when button is pressed, speed up the blink rate on LED #2
                    if(event.getState().isHigh()){
                    	been.blink(200);
                    	been.blink(200);
                    }                        
                    else{
                    	twoG.blink(1000);
                    	been.blink(1000);
                    }
                }
            });
*/
        
        while(true) {
        	
        	float  distance=checkdist();
        	
        	if(distance<0.49){
        		if(STAT!=2){
        			beepAndLightFast();
        		}
        		STAT=2;
        	}else if(distance<0.99){
        		if(STAT!=1){
        			beepAndLightSlow();
        		}
        		STAT=1;
        	}else {
        		if(STAT!=0){
        			stop();
        		}
        		STAT=0;
			}
        	
        	System.out.println("distance  "+distance+"  m");
        	
            Thread.sleep(1000);
        }
        
        // stop all GPIO activity/threads
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
    }
}
