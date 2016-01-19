package com.zaq.smartHome.pi4j.rf;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;

import java.util.BitSet;
/**
 * 315M 发送测试
 * 2262/2272 M4模块脉冲 150,320,450,500,650,900,1320,1820
 * @author zaqzaq
 * 2016年1月19日
 *
 */
public class RF315_4SendTest {
	private final static String addr="FFFFFFFF";//全置空的8位地址码
	
	public static void main(String[] args) throws InterruptedException {
		RF315_4SendTest transmitter = new RF315_4SendTest(RaspiPin.GPIO_27);
		 String tmp="";
/*		for(int i1=0;i1<3;i1++){
			for(int i2=0;i2<3;i2++){
				for(int i3=0;i3<3;i3++){
					for(int i4=0;i4<3;i4++){
								tmp=(i1==2?"F":(i1+""))+
										(i2==2?"F":(i2+""))+
										(i3==2?"F":(i3+""))+
										(i4==2?"F":(i4+""));
								
								transmitter.sendTriState(addr+tmp);
								
					}
				}
			}
		}*/
		 transmitter.sendTriState(addr+"FFFF");//我的B键的值：450微秒脉冲 发4次
		
		  System.out.println("发完了");
	}
	
    private final GpioPinDigitalOutput transmitterPin;

    private final int pulseLength = 450; //150,320,450,500,650,900,1320,1820
    private final int repeatTransmit = 4;//重复发4次

    public RF315_4SendTest(Pin transmitterPin) {
        final GpioController gpio = GpioFactory.getInstance();
        this.transmitterPin = gpio.provisionDigitalOutputPin(transmitterPin);
        this.transmitterPin.setShutdownOptions(true);
    }


    /**
     * Sends a Code Word
     *
     * @param codeWord /^[10FS]*$/ -> see getCodeWord
     */
    private void sendTriState(String codeWord) {
    	System.out.println("发出："+codeWord);
        for (int nRepeat = 0; nRepeat < repeatTransmit; nRepeat++) {
            for (int i = 0; i < codeWord.length(); ++i) {
                switch (codeWord.charAt(i)) {
                    case '0':
                        this.sendT0();
                        break;
                    case 'F':
                        this.sendTF();
                        break;
                    case '1':
                        this.sendT1();
                        break;
                }
            }
            this.sendSync();
        }
    }

    /**
     * Sends a "Sync" Bit
     *                       _
     * Waveform Protocol 1: | |_______________________________
     *                       _
     * Waveform Protocol 2: | |__________
     */
    private void sendSync() {
        this.transmit(1, 31);
    }

    /**
     * Sends a Tri-State "0" Bit
     *            _     _
     * Waveform: | |___| |___
     */
    private void sendT0() {
        this.transmit(1, 3);
        this.transmit(1, 3);
    }

    /**
     * Sends a Tri-State "1" Bit
     *            ___   ___
     * Waveform: |   |_|   |_
     */
    private void sendT1() {
        this.transmit(3, 1);
        this.transmit(3, 1);
    }

    /**
     * Sends a Tri-State "F" Bit
     *            _     ___
     * Waveform: | |___|   |_
     */
    private void sendTF() {
        this.transmit(1, 3);
        this.transmit(3, 1);
    }

    private void transmit(int nHighPulses, int nLowPulses) {
        if (this.transmitterPin != null) {
            this.transmitterPin.high();
            Gpio.delayMicroseconds(this.pulseLength * nHighPulses);

            this.transmitterPin.low();
            Gpio.delayMicroseconds(this.pulseLength * nLowPulses);
        }
    }

    /**
     * convenient method to convert a string like "11011" to a BitSet.
     *
     * @param address
     * @return a bitset containing the address that can be used for switchOn()/switchOff()
     */
    public static BitSet getSwitchGroupAddress(String address) {
        if (address.length() != 5) {
            throw new IllegalArgumentException("the switchGroupAddress must consist of exactly 5 bits!");
        }
        BitSet bitSet = new BitSet(5);
        for (int i = 0; i < 5; i++) {
            bitSet.set(i, address.charAt(i) == '1');
        }
        return bitSet;
    }

}