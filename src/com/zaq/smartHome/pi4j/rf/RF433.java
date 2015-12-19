package com.zaq.smartHome.pi4j.rf;

import java.util.BitSet;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.zaq.smartHome.util.ThreadPool;

public class RF433 {
	private static Logger logger=Logger.getLogger(RF433.class);
	public static void main(String[] args) throws Exception {
	System.out.println((long) (350 * 120 * 0.01));
//		ThreadPool.execute(new Runnable() {
//			
//			@Override
//			public void run() {
				 final GpioController gpio = GpioFactory.getInstance();
			        // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
			        final GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_28, PinPullResistance.PULL_DOWN);
			        myButton.setShutdownOptions(true);
			        // create and register gpio pin listener
			        myButton.addListener(new GpioPinListenerDigital() {
			        	private long time=0;
			            @Override
			            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
			                // display pin state on console
			            	logger.info(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
			                
			                long now= System.nanoTime();
			                logger.info("now:"+now);
			                if(0!=time){
			                	logger.info("上次间隔"+(now-time)/1000+"微秒");
			                }
			                time=now;;
			            }
			            
			        });
			        
			        logger.info(" ... complete the GPIO #28 circuit and see the listener feedback here in the console.");
			        
			        // keep program running until user aborts (CTRL-C)
			        for (;;) {
			            Thread.sleep(500);
			        }
			        
//			}
//		});
		
//		Thread.sleep(5000);
//		
//		ThreadPool.execute(new Runnable() {
//			
//			@Override
//			public void run() {
////				while(true){
//					BitSet address = RCSwitch.getSwitchGroupAddress("01011");
//
//					RCSwitch transmitter = new RCSwitch(RaspiPin.GPIO_29);
//					transmitter.switchOn(address, 1); // switches the switch unit A (A = 1,
//														// B = 2, ...) on
//					logger.info("on close");
//					try {
//						Thread.sleep(5000); // wait 5 sec.
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					transmitter.switchOff(address, 1);
//					logger.info("off close");
//				}
////			} 
//		});
//		
	}
}
