package com.zaq.smartHome.controll.command;

import java.util.WeakHashMap;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.zaq.smartHome.controll.BaseCmd;
import com.zaq.smartHome.db.bean.Cmd;
import com.zaq.smartHome.pi4j.BaseGpio;

/**
 * gpio引脚控制的指令
 * @author zaqzaq
 * 2015年12月23日
 *
 */
public class GpioCmd extends BaseCmd{
	protected GpioController gpio = GpioFactory.getInstance();
	/**
	 * gpio自定义输出引脚缓存
	 */
	protected static WeakHashMap<Pin, GpioPinDigitalOutput> outPinsCache=new WeakHashMap<>();
	
	public GpioCmd(Cmd cmd) {
		super(cmd);
	}
	public GpioCmd(Cmd cmd, Integer delay) {
		super(cmd, delay);
	}
	public GpioCmd(Cmd cmd, Integer delay,Long userCreate) {
		super(cmd, delay,userCreate);
	}

	/**
	 * 执行gpio类型的引脚控制指令
	 */
	@Override
	protected void exec() {
		int pinCode=Integer.valueOf(this.getCommand().getCode());
		
		Pin pin= BaseGpio.getGpioPin(Math.abs(pinCode));
		
		GpioPinDigitalOutput digitalOutput=null;
		if(outPinsCache.containsKey(pin)){
			digitalOutput=outPinsCache.get(pin);
		}else{
			digitalOutput=gpio.provisionDigitalOutputPin(pin);
			digitalOutput.setShutdownOptions(true);
			outPinsCache.put(pin, digitalOutput);
		}
		
		if(pinCode>-1){
			digitalOutput.high();//输出高电平
		}else{
			digitalOutput.low();//输出低电平
		}
		
	}
	
	public static void main(String[] args) {
		int pinCode=11;
		System.out.println(BaseGpio.getGpioPin(Math.abs(pinCode)).getName());
	}
}
