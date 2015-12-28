package com.zaq.smartHome.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;

import org.apache.log4j.Logger;

import com.zaq.smartHome.baidu.STTutil;
import com.zaq.smartHome.baidu.TTSutil;
import com.zaq.smartHome.db.YuYinDB;
import com.zaq.smartHome.db.bean.YuYin;
import com.zaq.smartHome.exception.SystemException;
import com.zaq.smartHome.qa.robot.QAxiaoDu;
import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.PinyingUtil;
import com.zaq.smartHome.util.ThreadPool;
/**
 * 音频工具类
 * @author zaqzaq
 * 2015年12月12日
 *
 */
public class AudioUtil {
	private static Logger logger=Logger.getLogger(AudioUtil.class);
	/**
	 * 录音的临时文件路径
	 */
	public static final String AD_TMP="sound" + File.separator + "tmp";
	/**
	 * 自己录音的初始化音频文件
	 */
	public static final String AD_INIT="sound" + File.separator + "init";
	/**
	 * 第三方转化的音频文件
	 */
	public static final String AD_CONVER="sound" + File.separator + "conver";
	
	/**
	 * 播个语音合成失败 提示音
	 */
	public static void playTTSFail(){
		try {
			Player.play(new File(AD_INIT+File.separator+"tts_fail.wav"));
		} catch (Exception e) {
			logger.error("播个语音合成失败都不行么！XXX", e);
		}
	}
	/**
	 * 播放录音失败的语音提示
	 */
	public static void playRecordFail(){
		try {
			Player.play(new File(AD_INIT+File.separator+"record_fail.wav"));
		} catch (Exception e) {
			logger.error("播个录音失败都不行么！XXX", e);
		}
	}
	
	/**
	 * 播放语音识别异常语音提示
	 */
	public static void playRecognitionFail(){
		try {
			Player.play(new File(AD_INIT+File.separator+"recognition_fail.wav"));
		} catch (Exception e) {
			logger.error("播个语音识别异常都不行么！XXX", e);
		}
	}
	/**
	 * 回答不上来的 语音提示
	 */
	public static void playASKFail(){
		try {
			Player.play(new File(AD_INIT+File.separator+"ask_fail.wav"));
		} catch (Exception e) {
			logger.error("回答不上来都不行么！XXX", e);
		}
	}
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

	public static void done(){
		done(Record.take());
	}
	/**
	 * 识别默认语音文件 并 处理指令 或QA
	 * @return
	 * @throws Exception
	 */
	public static void done(final File recordFile){
		ThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				//进行语音识别
				String text=null;
				try {
					text= STTutil.done(recordFile);
					logger.info("识别出语音："+text);
				} catch (Exception e) {
					logger.error("语音识别异常", e);
					playRecognitionFail();
					return;
				}finally{
					if(recordFile.exists()){
						recordFile.delete();
					}
				}
					
				String ask=null;
				try {
					ask = QAxiaoDu.instance().ask(text);
					logger.info("获取回答："+ask);
				} catch (Exception e) {
					logger.error("获取回答异常", e);
					playASKFail();
					return;
				} 
				
				if(null==ask){
					return;
				}
				
				
				Player.playText(ask);
//				//先从数据库缓存中找相同ask的信息
//				YuYin yy=YuYinDB.getByTextAndUse(ask);
//				
//				if(null!=yy){
//					Player.play(yy.getPath());
//					return;
//				}
//				
//				String toFilePath=AudioUtil.AD_CONVER+File.separator+PinyingUtil.getPinYinHeadCharWithAz(ask)+System.currentTimeMillis()+".wav";
//				
//				try {
//					TTSutil.done(ask,toFilePath);
//				} catch (Exception e) {
//					logger.error("文字【"+ask+"】转语音失败",e);
//					playTTSFail();
//				}
//				//添加语音到指令库
//				YuYinDB.add(ask, toFilePath);
			}
		});
	}
	
	public static void main(String[] args) throws Exception {
		AppUtil.init();
		final StringBuilder text=new StringBuilder();;
		ThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					text.append(STTutil.done(Record.captureRetByte("tmpSound.wav"), "wav")); 
					logger.error("x:"+text.toString());
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread.sleep(3000l);
		Record.stop();
		//等STT解析完
		Thread.sleep(5000l);
		
		final String audioFilePath=AD_INIT+File.separator+"meSay2bd.wav";
		logger.error("xx:"+text.toString());
		try {
			TTSutil.done(text.toString(),audioFilePath,true,new Runnable() {
				@Override
				public void run() {
					try {
						Player.play(new File(audioFilePath));
					} catch (LineUnavailableException | SystemException | IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			playTTSFail();
		}
		
		
	}
}
