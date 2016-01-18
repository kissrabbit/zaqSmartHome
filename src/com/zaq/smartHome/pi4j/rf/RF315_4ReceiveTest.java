package com.zaq.smartHome.pi4j.rf;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
/**
 * PT2272 模块测试接收
 * @author zaqzaq
 * 2016年1月18日
 *
 */
public class RF315_4ReceiveTest
{

  public static void main(String[] args) throws InterruptedException
  {

    GpioController gpio = GpioFactory.getInstance();
    
    
    GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_28, PinPullResistance.PULL_DOWN);
   
    myButton.setShutdownOptions(true);
    myButton.addListener( new GpioPinListenerDigital()
    {

      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
      {
    	  System.out.println("接收到："+event.getState().getValue());
      }

    
    });
    
    
    System.out.println(" ... complete the GPIO #28 circuit and see the listener feedback here in the console.");
    while (true)
    {

      Thread.sleep(300);
    }
  }
}