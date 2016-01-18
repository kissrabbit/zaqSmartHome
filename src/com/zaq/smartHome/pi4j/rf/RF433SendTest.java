package com.zaq.smartHome.pi4j.rf;

import java.util.Scanner;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
/**
 * 443M 无线射频发射
 * @author zaqzaq
 * 2015年12月28日
 *
 */
public class RF433SendTest {
	private final GpioPinDigitalOutput transmitterPin;
	private final int nRepeatTransmit = 10;
	private int nPulseLength = 350;
	private int nProtocol = 1;

	public static void main(String[] args) {

		RF433SendTest client = new RF433SendTest(RaspiPin.GPIO_27, 1);

		String cod = "";
		while (!cod.equalsIgnoreCase("exit")) {
			Scanner ob = new Scanner(System.in);
			System.out.print("Enter code: ");
			cod = ob.nextLine();

			client.send(Integer.parseInt(cod), 3);
		}

	}

	public RF433SendTest(Pin transmitterPin, int nProtocol) {

		GpioController gpio = GpioFactory.getInstance();
		this.transmitterPin = gpio.provisionDigitalOutputPin(transmitterPin);
		this.transmitterPin.setShutdownOptions(true);
		this.nProtocol = nProtocol;
		if (nProtocol == 1)
			nPulseLength = 350;
		else if (nProtocol == 2)
			nPulseLength = 650;
		else if (nProtocol == 3)
			nPulseLength = 100;

	}

	public void send(int code, int length) {
		send(dec2binWzerofill(code, length));
	}

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

	private char[] dec2binWzerofill(int dec, int bitLength) {
		String strBin = "";
		String binary = Integer.toBinaryString(dec);
		for (int i = 0; i < (bitLength - binary.length()); i++) {
			strBin += "0";
		}
		strBin += binary + "\0";
		char[] bin = strBin.toCharArray();

		System.out.println(bin);
		return bin;
	}

	/**
	 * Sends a "Sync" Bit _ Waveform Protocol 1: |
	 * |_______________________________ _ Waveform Protocol 2: | |__________
	 */
	private void sendSync() {
		if (nProtocol == 1)
			transmit(1, 31);
		else if (nProtocol == 2)
			transmit(1, 10);
		else if (nProtocol == 3)
			transmit(1, 71);
	}

	/**
	 * Sends a "0" Bit _ Waveform Protocol 1: | |___ _ Waveform Protocol 2: |
	 * |__
	 */
	private void send0() {
		if (nProtocol == 1)
			transmit(1, 3);
		else if (nProtocol == 2)
			transmit(1, 2);
		else if (nProtocol == 3)
			transmit(4, 11);
	}

	/**
	 * Sends a "1" Bit ___ Waveform Protocol 1: | |_ __ Waveform Protocol 2: |
	 * |_
	 */
	private void send1() {
		if (nProtocol == 1)
			transmit(3, 1);
		else if (nProtocol == 2)
			transmit(2, 1);
		else if (nProtocol == 3)
			transmit(9, 6);
	}

	private void transmit(int nHighPulses, int nLowPulses) {
		if (this.transmitterPin != null) {
			// Gpio.digitalWrite(0, 1);
			this.transmitterPin.high();
			Gpio.delayMicroseconds(nPulseLength * nHighPulses);
			// Gpio.digitalWrite(0, 0);
			this.transmitterPin.low();
			Gpio.delayMicroseconds(nPulseLength * nLowPulses);
		}
	}

	// pin00 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
	//
	// if (Gpio.wiringPiSetup() == -1) {
	// System.out.println(" ==>> GPIO SETUP FAILED");
	// return;
	// }
	//
	// GpioUtil.export(0, 1);
	// Gpio.pinMode(0, 1);
}