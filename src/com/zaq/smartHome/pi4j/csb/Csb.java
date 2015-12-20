package com.zaq.smartHome.pi4j.csb;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;

import org.apache.log4j.Logger;

import com.zaq.smartHome.baidu.STTutil;
import com.zaq.smartHome.exception.ResRuningException;
import com.zaq.smartHome.exception.SystemException;
import com.zaq.smartHome.pi4j.BaseGpio;
import com.zaq.smartHome.pi4j.been.Been;
import com.zaq.smartHome.pi4j.diode.Diode;
import com.zaq.smartHome.sound.AudioUtil;
import com.zaq.smartHome.sound.Player;
import com.zaq.smartHome.sound.Record;
import com.zaq.smartHome.util.ThreadPool;

/**
 * 超声波
 * @author zaqzaq
 * 2015年12月20日
 *
 */
public class Csb extends BaseGpio{
	protected Csb(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}
	
	protected static Logger logger=Logger.getLogger(Csb.class);
	private static Csb csb; //Singleton 
	private static boolean hasInit=false;//初始化是否成功
	private static final String outGpioName="gpio.csb.send";//配置文件对映的名称
	private static final String inGpioName="gpio.csb.rec";//配置文件对映的名称
	private static Executor executor;//测距的线程
	private static Executor executorRecord;//录音的线程
	private static boolean runFlag=false;//是否开启测距
	private static boolean recordFail=false;//录音失败
	private static int STAT=0;//工作状态 0;1米外  1:1米内 2：0.5米内
	
	//Singleton
	public static Csb instace(){
		if(null==csb){
			try {
				csb=new Csb(inGpioName,outGpioName);
				executor=Executors.newSingleThreadExecutor();
				executorRecord=Executors.newSingleThreadExecutor();
				hasInit=true;
			} catch (Exception e) {
				logger.error("初始化超声波失败", e);
				hasInit=false;
			}
		}
		return csb;
	}
	/**
	 * 开启超声波测距
	 */
	public void run(){
		if(hasInit){
			runFlag=true;
			while(runFlag) {
	        	
	        	float  distance=checkdist();
	        	
	        	if(distance<0.49){
	        		if(STAT!=2){
	        			//打开录音机
	        			executorRecord.execute(new Runnable() {
							
							@Override
							public void run() {
								try {
									Record.captureRetFile();
									recordFail=false;
								} catch (LineUnavailableException | ResRuningException | SystemException e) {
									logger.error("录音失败", e);
									recordFail=true;
									AudioUtil.playRecordFail();
								}
							}
						});
	        		}
	        		STAT=2;
	        	}else if(distance<0.99){
	        		if(STAT!=1){
	        			//关闭录音机
	        			finishRecord();
	        			//发光二极管闪  提示2秒 靠近一点
	        			Diode.instace().runSlowDuration(2000);
	        		}
	        		STAT=1;
	        	}else {
	        		if(STAT!=0){
	        			//关闭录音机
	        			finishRecord();
	        		}
	        		STAT=0;
				}
	        	
	            try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
	        }
		}
	}
	
	public void stop(){
		runFlag=false;
	}
	
	/**
	 * 完成录音
	 */
	private void finishRecord(){
		Record.stop();
		if(!recordFail){
			AudioUtil.done();
		}
	}
	/**
	 * 檢測距離
	 * @return
	 * @throws InterruptedException
	 */
	private  float  checkdist() {
//		System.out.println("send s start...");
        //发出触发信号
        output.high();
        //保持10us以上（这里选择15us）
        try {
			Thread.sleep(0, 15000);
		} catch (InterruptedException e) {
			logger.error("发送超声波失败",e);
			return Float.MAX_VALUE;
		}
        output.low();
        //妈的太近了，没有这句话，你就等着下面的死循环吧-_-!
        FutureTask<Void> future=new FutureTask<Void>(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				 while (input.isLow()){
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
			logger.error("Do not handle,too close....",e);
        	return 0;
		}
        //发送完毕 发现高电平时开时计时
//        System.out.println("Sent to the end of the discovery of high power usually open time");
        long t1 = System.nanoTime();
        while (input.isHigh()){
        	//不用处理，，，设备正在接收超声波。。。。
//        	System.out.println("Do not handle, and the device is receiving ultrasound....");
        }
        //接收到回应 高电平结束停止计时
//        System.out.println("Received in response to the high end stop time");
        long t2 = System.nanoTime();
        //返回距离，单位为米
        return ((float)(t2-t1)/1000000000)*340/2;
	}
}
