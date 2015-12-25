package com.zaq.smartHome.pi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.util.StringUtil;
import com.zaq.smartHome.exception.GpioException;
import com.zaq.smartHome.util.AppUtil;

public class BaseGpio extends RaspiPin{
	protected GpioController gpio = GpioFactory.getInstance();
	/**
	 * 输入信号 需要监听
	 */
	protected final GpioPinDigitalInput input; 
	/**
	 * 输出信号
	 */
	protected final GpioPinDigitalOutput output; 
	
	protected BaseGpio(String inputGpioName,String outputGpioName) throws Exception{
		if(StringUtil.isNullOrEmpty(inputGpioName)&&StringUtil.isNullOrEmpty(outputGpioName)){
			throw new GpioException("初始化引脚不能为空");
		}
		if(StringUtil.isNotNullOrEmpty(inputGpioName)){
			input=initInput(inputGpioName);
		}else{
			input=null;
		}
		
		if(StringUtil.isNotNullOrEmpty(outputGpioName)){
			output=initOutput(outputGpioName);
		}else{
			output=null;
		}
	}
	protected GpioPinDigitalInput initInput(String inputGpioName) throws Exception{
		
		GpioPinDigitalInput digitalInput=gpio.provisionDigitalInputPin(getGpioPin(inputGpioName), PinPullResistance.PULL_DOWN);
		digitalInput.setShutdownOptions(true);
		return digitalInput;
	}
	protected GpioPinDigitalOutput initOutput(String outputGpioName) throws Exception{
		GpioPinDigitalOutput digitalOutput=gpio.provisionDigitalOutputPin(getGpioPin(outputGpioName));
		digitalOutput.setShutdownOptions(true);
		return digitalOutput;
	}
	
	protected Pin getGpioPin(String gpioName)throws Exception{
		Integer pinCode=Integer.valueOf(AppUtil.getPropertity(gpioName));
		
		if(pinCode<1||pinCode>29){
			throw new GpioException("引脚："+pinCode+"不存在");
		}
		
		return createDigitalAndPwmPin(pinCode, "GPIO #"+pinCode);
	}
	
	/**
	 * 按wirtePin 获取取引脚对象
	 * @param pinCode wpi引脚编码
	 * @return
	 * @throws Exception
	 */
	public static Pin getGpioPin(int pinCode)throws GpioException{
		if(pinCode<0||pinCode>29){
			throw new GpioException("引脚："+pinCode+"不存在");
		}
		
		Pin pin=getPinByAddress(pinCode);
		if(null==pin){
			pin=createDigitalAndPwmPin(pinCode, "GPIO #"+pinCode);
		}
		return pin;
	}
	
}
