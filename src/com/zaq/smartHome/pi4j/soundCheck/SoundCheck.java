package com.zaq.smartHome.pi4j.soundCheck;

import com.zaq.smartHome.pi4j.BaseGpio;
/**
 * 声控检测模块
 * @author zaqzaq
 * 2015年12月22日
 *
 */
public class SoundCheck extends BaseGpio{

	protected SoundCheck(String inputGpioName, String outputGpioName) throws Exception {
		super(inputGpioName, outputGpioName);
	}

}
