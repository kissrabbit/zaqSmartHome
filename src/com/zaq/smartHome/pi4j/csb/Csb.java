package com.zaq.smartHome.pi4j.csb;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.LineUnavailableException;

import com.zaq.smartHome.exception.ResRuningException;
import com.zaq.smartHome.exception.SystemException;
import com.zaq.smartHome.pi4j.BaseGpio;
import com.zaq.smartHome.pi4j.been.Been;
import com.zaq.smartHome.pi4j.diode.Diode;
import com.zaq.smartHome.sound.AudioUtil;
import com.zaq.smartHome.sound.Record;

/**
 * 超声波HC-SR04 模块控制 5V
 * @author zaqzaq
 * 2015年12月20日
 *
 */
public class Csb extends BaseGpio{
	protected Csb(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}
	private static final int CHECK_COUNT=1;//检测到了两次再做出回应，防止超时波的误差
	private static Csb csb; //Singleton 
	private static final String outGpioName="gpio.csb.send";//配置文件对映的名称 trig（控制端）
	private static final String inGpioName="gpio.csb.rec";//配置文件对映的名称  echo（接收端）
	private static Executor executor;//测距的线程
	private static Executor executorRecord;//录音的线程
	private static Executor executorRun;//运行的线程
	private static boolean runFlag=false;//是否开启测距
	private static boolean recordFail=false;//录音失败
	private static int STAT=0;//工作状态 0;1米外  1:1米内 2：0.5米内
	
	//Singleton
	public static Csb initOrGet(){
		if(null==csb){
			try {
				csb=new Csb(inGpioName,outGpioName);
				executor=Executors.newSingleThreadExecutor();
				executorRecord=Executors.newSingleThreadExecutor();
				executorRun=Executors.newSingleThreadExecutor();
			} catch (Exception e) {
				logger.error("初始化超声波失败", e);
				csb.hasInit=false;
			}
		}
		return csb;
	}
	/**
	 * 开启超声波测距
	 */
	public void run(){
		if(hasInit){
			executorRun.execute(new Runnable() {
				int chechCount=CHECK_COUNT;//检测到二次刚录音，防止超时波的误差
				@Override
				public void run() {

					runFlag=true;
					while(runFlag) {
			        	
			        	float  distance=checkdist();
			        	logger.debug("超声波检测距离："+distance+"m");
			        	
			        	if(distance==0){
			        		Been.initOrGet().runSlowDuration(1000);
			        	//0.49m可以打开录音机	
			        	}else if(distance<0.49){
			        		if(STAT!=2&&chechCount==0){
			        			chechCount=CHECK_COUNT;
			        			//打开录音机
			        			Diode.initOrGet().runFastDuration(1000);
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
			        			STAT=2;
			        		}else{
			        			chechCount--;
			        		}
			        	}else if(distance<0.99){
			        		chechCount=CHECK_COUNT;
			        		if(STAT!=1){
			        			//关闭录音机
			        			finishRecord();
			        			//发光二极管闪  提示2秒 靠近一点
			        			Diode.initOrGet().runSlowDuration(1000);
			        		}
			        		STAT=1;
			        	}else {
			        		chechCount=CHECK_COUNT;
			        		if(STAT!=0){
			        			//关闭录音机
			        			finishRecord();
			        		}
			        		STAT=0;
						}
			        	
		        	    try {
							Thread.sleep(chechCount==CHECK_COUNT?800:100);
						} catch (InterruptedException e) {}
			        }
				
				}
			});
		}
	}
	
	public boolean isRun(){
		return runFlag;
	}
	
	public void stop(){
		STAT=0;
		runFlag=false;
		logger.debug("关闭超声波测距");
		finishRecord();
	}
	
	/**
	 * 完成录音
	 */
	private void finishRecord(){
		//中断录音前是否正在录音
		boolean isRecordIng=Record.isRecordIng();
		if(isRecordIng){
			Record.stop();
		}
		if(!recordFail&&isRecordIng){
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
