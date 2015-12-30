package com.zaq.smartHome.sound;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.apache.log4j.Logger;

import com.zaq.smartHome.baidu.TTSutil;
import com.zaq.smartHome.db.YuYinDB;
import com.zaq.smartHome.db.bean.YuYin;
import com.zaq.smartHome.exception.SystemException;
import com.zaq.smartHome.pi4j.been.Been;
import com.zaq.smartHome.util.PinyingUtil;
import com.zaq.smartHome.util.ThreadPool;

/**
 * 音频播放
 * 
 * @author zaqzaq 2015年12月11日
 * 
 */
public class Player {
	private static Logger logger = Logger.getLogger(Player.class);

	private Player() {
	}

	// 播音设备
	private static SourceDataLine sd = null;
	// 定义存放录音的字节数组,作为缓冲区
	private static byte bts[] = new byte[10000];

	/**
	 * 播放音频
	 * 
	 * @param audioData
	 * @throws LineUnavailableException
	 * @throws SystemException
	 */
	public static synchronized void play(InputStream audioIs) throws LineUnavailableException, SystemException {
		audioIs = new BufferedInputStream(audioIs);
		AudioInputStream ais = null;
		try {
			// 获取音频输入流
			ais = AudioSystem.getAudioInputStream(audioIs);
			// 获取音频编码对象
			AudioFormat audioFormat = ais.getFormat();
			// 打开播音设备
			openSD(audioFormat);
			logger.debug("开始播放音频");
			int cnt;
			// 读取数据到缓存数据
			while ((cnt = ais.read(bts, 0, bts.length)) != -1) {
				if (cnt > 0) {
					// 写入缓存数据
					// 将音频数据写入到混频器
					sd.write(bts, 0, cnt);
				}
			}

		} catch (Exception e) {
			throw new SystemException("播音失败", e);
		} finally {
			try {
				// 关闭流
				if (ais != null) {
					ais.close();
				}
				if (audioIs != null) {
					audioIs.close();
				}
			} catch (Exception e) {
			}
			// sd.drain();
			sd.close();
		}

		logger.debug("音频播放完毕");
	}
	/**
	 * 用JAVA自带的播放音频
	 * @param audioFile
	 * @throws LineUnavailableException
	 * @throws SystemException
	 * @throws IOException
	 */
	public static void play(File audioFile) throws LineUnavailableException, SystemException, IOException {
		play(new FileInputStream(audioFile));
	}
	
	/**
	 * 音频播放文字
	 * @param text 文本
	 */
	public static void playText(String text){
		//先从数据库缓存中找相同ask的信息
		YuYin yy=YuYinDB.getByTextAndUse(text);
		//存在语音且音频文件存在
		if(null!=yy&&new File(yy.getPath()).exists()){
			play(yy.getPath());
			return;
		}
		
		final String toFilePath=AudioUtil.AD_CONVER+File.separator+PinyingUtil.getPinYinHeadCharWithAz(text)+System.currentTimeMillis()+".wav";
		
		try {
	  		//不用jave做音频转换 ,用mplayer软件播放
			TTSutil.done(text,toFilePath,false,new Runnable() {
	    			@Override
	    			public void run() {
	    				try {
	    					play(toFilePath);//直接用命令行 mplayer播放
	    				} catch (Exception e) {
	    					logger.error("播放文件："+toFilePath+"失败", e);
	    					//轰鸣器 提示两秒
	    					Been.initOrGet().runFastDuration(2000);
	    				}
	    			}
	    		});
		} catch (Exception e) {
			logger.error("文字【"+text+"】转语音失败",e);
			AudioUtil.playTTSFail();
		}
		//添加语音到指令库
		YuYinDB.add(text, toFilePath);
	}
	
	/**
	 * 用mplayer 软件播放音频
	 * @param toFilePath 音频路径
	 */
	public static void play(final String toFilePath) {
		ThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				InputStreamReader ir=null;
				Process process=null;
				String commands = "mplayer "+toFilePath;
				try {
					
					logger.info("mplayer开始播放音频:"+commands);
					//一次只播放一个
					synchronized(Player.class){
						process = Runtime.getRuntime().exec(commands);
					}
					
					logger.info("mplayer结束播放音频:"+commands);
					ir = new InputStreamReader(process.getInputStream());
					BufferedReader input = new BufferedReader(ir);
					String line;
					while ((line = input.readLine()) != null) {
						logger.debug(line);
					}
				} catch (IOException e) {
					logger.error("mplayer播放音频:"+commands+"异常" , e);
					Been.initOrGet().runFastDuration(3000);
				}finally{
					if(null!=ir){
						try {
							ir.close();
						} catch (IOException e) {}
					}
					if(null!=process){
						process.destroy();
					}
				}
			}
		});
		
	}

	// 打开播音设备
	private static void openSD(AudioFormat af) throws LineUnavailableException {
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, af);
		sd = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sd.open(af);
		sd.start();
	}

	public static void main(String[] args) throws LineUnavailableException, SystemException, IOException {
		// play(new File(AudioUtil.TMP_RECORD));
		// play(new File(AudioUtil.AD_INIT+File.separator+"meSay2bd.wav"));
		// play(new File("sound"+File.separator+"test1.wav"));
		play(new File("sound" + File.separator + "xxx_new.wav"));
		// play(new File("sound"+File.separator+"test1.mp3"));

		// File musicFile=new
		// File("sound"+File.separator+"test1.wav");//得到一个MP3文件,不加斜杠表示根目录
		// if(musicFile.exists()){
		// MediaLocator locator=new
		// MediaLocator("file:"+musicFile.getAbsolutePath());
		// javax.media.Player player=Manager.createPlayer(locator);
		// player = Manager.createRealizedPlayer(locator);
		// player.prefetch();// 预读文件
		// player.start();//播放
		// }else{
		// System.err.println(musicFile+"  找不到");
		// }

	}

}
