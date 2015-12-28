package com.zaq.smartHome.pi4j.rf;

import com.pi4j.wiringpi.Gpio;
import com.zaq.smartHome.pi4j.BaseGpio;
/**
 * 433M超再生发射模块
 * @author zaqzaq
 * 2015年12月28日
 *
 */
public class RF433 extends BaseGpio{
	protected RF433(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}
	private static RF433 rf433; //Singleton 
	private final int nRepeatTransmit = 10;
	private RF433Protocol protocol=RF433Protocol.ONE; //使用第一种协议
	private static final String gpioName="gpio.rf433.send";//配置文件对映的名称
	//Singleton
	public static RF433 initOrGet(){
		if(null==rf433){
			try {
				rf433=new RF433("",gpioName);
				logger.info("初始化RF433M模块成功"+rf433.input.getName());
			} catch (Exception e) {
				logger.error("初始化RF433M模块失败", e);
				rf433.hasInit=false;
			}
		}
		
		return rf433;
	}
	
	/**
	 * 发射数字code  默认最短路二进制长度为3
	 * @param code
	 */
	public void send(int code,RF433Protocol protocol){
		send(code, 3,protocol);
	}
	
	/**
	 * 发射一个code数字
	 * @param code 
	 * @param length 转成二制不满这个长度以0补全
	 */
	public synchronized void send(int code, int length,RF433Protocol protocol) {
		this.protocol=protocol;
		send(dec2binWzerofill(code, length));
	}

	/**
	 * 发射字符1或0
	 * @param sCodeWord
	 */
	private void send(char... sCodeWord) {

		for (int nRepeat = 0; nRepeat < nRepeatTransmit; nRepeat++) {
			int i = 0;
			while (sCodeWord[i] != '\0') {
				switch (sCodeWord[i]) {
				case '0':
					send0();
					break;
				case '1':
					send1();
					break;
				}
				i++;
			}
			sendSync();
		}
	}

	/**
	 * 装数字dec转成二进制的字符，不满bitLength的长度以0补全
	 * @param dec 数字
	 * @param bitLength  
	 * @return
	 */
	private char[] dec2binWzerofill(int dec, int bitLength) {
		String strBin = "";
		String binary = Integer.toBinaryString(dec);
		for (int i = 0; i < (bitLength - binary.length()); i++) {
			strBin += "0";
		}
		strBin += binary + "\0";
		char[] bin = strBin.toCharArray();

		return bin;
	}

	/**
	 * 发送同步码
	 */
	private void sendSync() {
		transmit(protocol.getSendSynHigh(), protocol.getSendSynLow());
	}

	/**
	 * 发送状态0
	 */
	private void send0() {
		transmit(protocol.getSend0High(), protocol.getSend0Low());
		if(protocol.isSendF()){
			transmit(protocol.getSend0High(), protocol.getSend0Low());
		}
	}

	/**
	 * 
	 * 发送状态1
	 */
	private void send1() {
		transmit(protocol.getSend1High(), protocol.getSend1Low());
		if(protocol.isSendF()){
			transmit(protocol.getSend1High(), protocol.getSend1Low());
		}
	}

	/**
	 * 发射悬置码F
	 */
	@SuppressWarnings("unused")
	private void sendF(){
		if(protocol.isSendF()){
			transmit(protocol.getSendFHigh(), protocol.getSendFLow());
			transmit(protocol.getSendFLow(), protocol.getSendFHigh());
		}
	}
	/**
	 * 形成高低波形
	 * @param nHighPulses
	 * @param nLowPulses
	 */
	private void transmit(int nHighPulses, int nLowPulses) {
		if (this.output != null) {
			this.output.high();
			Gpio.delayMicroseconds(protocol.getnPulseLength() * nHighPulses);
			this.output.low();
			Gpio.delayMicroseconds(protocol.getnPulseLength() * nLowPulses);
		}
	}
}
