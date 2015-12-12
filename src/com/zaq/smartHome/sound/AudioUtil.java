package com.zaq.smartHome.sound;

import java.io.File;

import javax.sound.sampled.AudioFormat;
/**
 * 音频工具类
 * @author zaqzaq
 * 2015年12月12日
 *
 */
public class AudioUtil {
	public static final String TMP_RECORD="sound" + File.separator + "tmpSound.wav";
	// 定义音频格式
	private static AudioFormat af = null;

	// 获取音频格式
	public static AudioFormat getAudioFormat8() {
		if (null != af) {
			return af;
		}
		/**
		 * 保证唯一
		 */
		//设置AudioFormat的参数
		synchronized (AudioUtil.class) {
			// 下面注释部分是另外一种音频格式，两者都可以
			AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
			float rate = 8000f;
			int sampleSize = 16;
			boolean bigEndian = true;
			int channels = 1;// 单声道为1，立体声为2
			return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);

		}
	}

	@Deprecated
	private AudioFormat getAudioFormat16() {
		// 下面注释部分是另外一种音频格式，两者都可以
		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
		// 采样率是每秒播放和录制的样本数
		float sampleRate = 16000.0F;
		// 采样率8000,11025,16000,22050,44100
		// sampleSizeInBits表示每个具有此格式的声音样本中的位数
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 单声道为1，立体声为2
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

}
