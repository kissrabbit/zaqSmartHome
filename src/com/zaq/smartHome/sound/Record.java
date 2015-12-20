package com.zaq.smartHome.sound;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

	//同步锁，防止录音未完成前 ，就使用录音文件
	final static Lock lock = new ReentrantLock();
    final static Condition waitStop  = lock.newCondition(); 

    final static ArrayBlockingQueue<File> recordFileQueue=new ArrayBlockingQueue<>(2);
    //录音文件名
    public static AtomicInteger  count=new AtomicInteger(0);
    
    /**
     * 获取最新的录音文件
     * @return
     */
    public static File take(){
    	try {
			return recordFileQueue.take();
		} catch (InterruptedException e) {
			return null;
		}
    }
	/**
	 * 停止录音
	 */
	public static void stop() {
		if(!stopflag){
			lock.lock();
			stopflag = true;
			try {
				waitStop.await();
			} catch (InterruptedException e) {
			}finally{
				lock.unlock();
			}
		}
		
	}
	/**
	 * 是否正在录音
	 * @return
	 */
	public static boolean isRecordIng(){
		return isRecordIng;
	}

	/**
	 * 录音
	 * 
	 * @return
	 * @throws LineUnavailableException
	 * @throws ResRuningException
	 * @throws SystemException
	 */
	public static byte[] captureRetByte(String recordTmpFileName) throws LineUnavailableException, ResRuningException, SystemException {
		File file=captureRetFile(recordTmpFileName);
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
	 * 录音 文件名以计数器的方式命名
	 * @throws LineUnavailableException
	 * @throws ResRuningException
	 * @throws SystemException
	 */
	public static void captureRetFile() throws LineUnavailableException, ResRuningException, SystemException{
		recordFileQueue.add(captureRetFile(count.getAndIncrement()+".wav"))  ;
	}
	
	/**
	 * 录音
	 * @param recordTmpFileName 录音临时文件名
	 * @return
	 * @throws LineUnavailableException
	 * @throws ResRuningException
	 * @throws SystemException
	 */
	public static File captureRetFile(String recordTmpFileName) throws LineUnavailableException, ResRuningException, SystemException {
		if (isRecordIng) {
			// 正在占用录音资源
			throw new ResRuningException("录音");
		}

		openTD();
		logger.debug("开始录音");
		// 将字节数组包装到流里，最终存入到baos中
		ByteArrayOutputStream baos = null;
		byte[] retByte = null;
		try {
			baos = new ByteArrayOutputStream();
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
				lock.lock();
				waitStop.signal();
				lock.unlock();
			}
		}

		if (null != retByte) {
			logger.debug("返回录音");
			return save(retByte,recordTmpFileName);
		}else{
			return null;
		}

		
	}
	
	/**
	 * 保存录音
	 * @param audioArrayByte 内存中录制的音频字节
	 * @param recordTmpFileName 录音临时文件名
	 * @return
	 * @throws SystemException
	 */
	private static File save(byte[] audioArrayByte,String recordTmpFileName) throws SystemException {
		
		ByteArrayInputStream bais = new ByteArrayInputStream(audioArrayByte);
		// 取得录音输入流
		AudioInputStream ais = new AudioInputStream(bais, AudioUtil.getAudioFormat8(), audioArrayByte.length / AudioUtil.getAudioFormat8().getFrameSize());
		// 定义最终保存的文件名
		// 写入文件
		try {
			// 以当前的时间命名录音的名字
			// 将录音的文件存放临时文件下
			File file = new File(AudioUtil.AD_TMP+File.separator+recordTmpFileName);
			file.createNewFile();
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
					System.out.println(STTutil.done(captureRetByte("tmpSound.wav"), "wav"));
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
					System.out.println(STTutil.done(captureRetFile("tmpSound.wav"), "pcm"));
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
