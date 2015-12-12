package com.zaq.smartHome.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.zaq.smartHome.baidu.STTutil;
import com.zaq.smartHome.exception.ResRuningException;
import com.zaq.smartHome.exception.SystemException;
import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.ThreadPool;
/**
 * 录音工具类
 * @author zaqzaq
 * 2015年12月11日
 *
 */
public class Record {
	private static Logger logger = Logger.getLogger(Record.class);

	private Record() {
	}

	// 定义目标数据行,可以从中读取音频数据,该 TargetDataLine 接口提供从目标数据行的缓冲区读取所捕获数据的方法。
	private static TargetDataLine td = null;
	private static boolean stopflag;
	private static DataLine.Info info = null;
	// 是否正在占用录音资源
	private static boolean isRecordIng = false;
	// 定义存放录音的字节数组,作为缓冲区
	private static byte bts[] = new byte[10000];

	/**
	 * 停止录音
	 */
	public static void stop() {
		stopflag = true;
	}

	/**
	 * 录音
	 * 
	 * @return
	 * @throws LineUnavailableException
	 * @throws ResRuningException
	 * @throws SystemException
	 */
	public static byte[] captureRetByte() throws LineUnavailableException, ResRuningException, SystemException {
		File file=captureRetFile();
		if(null!=file){
			try {
				return	FileUtils.readFileToByteArray(file);
			} catch (IOException e) {
				throw new SystemException("录音失败", e);
			}
		}else{
			return null;
		}
		
	}
	/**
	 * 录音
	 */
	public static File captureRetFile() throws LineUnavailableException, ResRuningException, SystemException {
		if (isRecordIng) {
			// 正在占用录音资源
			throw new ResRuningException("录音");
		}

		openTD();
		logger.debug("开始录音");
		// 将字节数组包装到流里，最终存入到baos中
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] retByte = null;
		try {
			stopflag = false;
			while (!stopflag) {
				// 当停止录音没按下时，该线程一直执行
				// 从数据行的输入缓冲区读取音频数据。
				// 要读取bts.length长度的字节,cnt 是实际读取的字节数
				int cnt = td.read(bts, 0, bts.length);
				if (cnt > 0) {
					baos.write(bts, 0, cnt);
				}
			}
			logger.debug("完成录音");
			retByte = baos.toByteArray();
		} catch (Exception e) {
			throw new SystemException("录音失败", e);
		} finally {
			isRecordIng = false;
			try {
				// 关闭打开的字节数组流
				baos.close();
			} catch (IOException e) {
			} finally {
				td.close();
			}
		}

		if (null != retByte) {
			logger.debug("返回录音");
			return save(retByte);
		}else{
			return null;
		}

		
	}
	
	/**
	 * 保存录音
	 * @param audioArrayByte 内存中录制的音频字节
	 * @return
	 * @throws SystemException
	 */
	private static File save(byte[] audioArrayByte) throws SystemException {
		
		ByteArrayInputStream bais = new ByteArrayInputStream(audioArrayByte);
		// 取得录音输入流
		AudioInputStream ais = new AudioInputStream(bais, AudioUtil.getAudioFormat8(), audioArrayByte.length / AudioUtil.getAudioFormat8().getFrameSize());
		// 定义最终保存的文件名
		// 写入文件
		try {
			// 以当前的时间命名录音的名字
			// 将录音的文件存放到F盘下语音文件夹下
			File file = new File(AudioUtil.TMP_RECORD);
			AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
			
			return file;
		} catch (Exception e) {
			throw new SystemException("保存录音失败", e);
		} finally {
			// 关闭流
			try {
				if (bais != null) {
					bais.close();
				}
				if (ais != null) {
					ais.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 打开录音设备
	 * 
	 * @throws LineUnavailableException
	 */
	private static void openTD() throws LineUnavailableException {
		if (null == info) {
			info = new DataLine.Info(TargetDataLine.class, AudioUtil.getAudioFormat8());
		}
		// 获取录音设备
		td = (TargetDataLine) (AudioSystem.getLine(info));
		// 打开具有指定格式的行，这样可使行获得所有所需的系统资源并变得可操作。
		td.open(AudioUtil.getAudioFormat8());
		// 允许某一数据行执行数据 I/O
		td.start();

		isRecordIng = true;
	}

	
	public static void main(String[] args) throws Exception {
		AppUtil.init();

		ThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println(STTutil.done(captureRetByte(), "wav"));
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		Thread.sleep(3000l);
		stop();
		Thread.sleep(1000l);
		ThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println(STTutil.done(captureRetFile(), "pcm"));
					;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//
		Thread.sleep(5000l);
		stop();
	}

}
