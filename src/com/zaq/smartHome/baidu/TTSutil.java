package com.zaq.smartHome.baidu;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.http.message.BasicNameValuePair;

import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.BDUtil;
import com.zaq.smartHome.util.HttpPoolUtil;
import com.zaq.smartHome.util.ThreadPool;
/**
 * 文字转语音
 * @author zaqzaq
 * 2015年12月5日
 *
 */
public class TTSutil {
	static final String API_URI="http://tsn.baidu.com/text2audio";
	
	//使用jave音频转码
	private static Encoder encoder = new Encoder();
	private static EncodingAttributes attrs;

    /**
     * 将tex文本转成语音字节
     * @param tex
     * @return
     * @throws Exception
     */
    private static byte[] done(String tex) throws Exception {
        return HttpPoolUtil.postRetMulti(API_URI, new BasicNameValuePair("tex", tex)
								        , new BasicNameValuePair("cuid", AppUtil.getPropertity("cuid"))
								        , new BasicNameValuePair("tok", BDUtil.getToken())
								        , new BasicNameValuePair("lan", "zh")
								        , new BasicNameValuePair("ctp", "1"));
        
    }
    /**
     * 将文字转换成音频文件后 运行 exec线程 (转换成wav格式后文件变大了8倍-_-!后续换jmf再试下)
     * @param tex 文本 
     * @param toFile  存储的目标音频文件
     * @param change2Wav 是否转换成wav格式
     * @param exec 文件储存后并发运行的线程
     * @throws Exception
     */
    public static File done(String tex ,String toFilePath ,boolean change2Wav,Runnable... execs) throws Exception {
    	byte[] doByte=done(tex);
    	
    	if(null!=doByte){
    		File toFile=new File(toFilePath);
    		FileUtils.writeByteArrayToFile(toFile,doByte );
    		if(change2Wav){
    			change(toFile);
    		}
        	for(Runnable exec:execs){
        		ThreadPool.execute(exec);
        	}
        	return toFile;
    	}else{
    		return null;
    	}
    	
    	
    	
    }
    /**
     * 将文字转换成音频文件后 运行 exec线程 (不转换成wav格式)
     * @param tex 文本 
     * @param toFile  存储的目标音频文件
     * @param exec 文件储存后并发运行的线程
     * @throws Exception
     */
    public static File done(String tex ,String toFilePath ,Runnable... execs) throws Exception {
    	return done(tex, toFilePath, true, execs);
    }
    
    /**
     * 转成java 支持的wav格式 
     * @param source
     * @throws IllegalArgumentException
     * @throws InputFormatException
     * @throws EncoderException
     */
    private static void change(File source) throws IllegalArgumentException, InputFormatException, EncoderException{
    	encoder.encode(source, source, getAttrs());
    }
    
    public static EncodingAttributes getAttrs(){
    	if(null!=attrs){
    		return attrs;
    	}
    		AudioAttributes audio = new AudioAttributes();
//        	audio.setCodec("libmp3lame");
        	audio.setBitRate(new Integer(8));
        	audio.setChannels(new Integer(1));//单声道
//        	audio.setVolume(250);
        	audio.setSamplingRate(new Integer(8000));
        	attrs = new EncodingAttributes();
        	attrs.setFormat("wav");
        	attrs.setAudioAttributes(audio);
    	return attrs;
    }
    
    public static void main(String[] args) throws Exception {
    	AppUtil.init();
//    	byte[] soundByte= done("果果乖，果果乖，果果乖，果果乖，乖乖乖乖，不要哭哦！再哭就要打屁股哦，乖哦！");
//    	
//        
//        FileOutputStream fileOutputStream=new FileOutputStream(file);
//        fileOutputStream.write(soundByte);
//        fileOutputStream.flush();
//        fileOutputStream.close();
    	done("果果乖，果果乖，果果乖，果果乖，乖乖乖乖，不要哭哦！再哭就要打屁股哦，乖哦！","sound"+File.separator+"test.wav");
    }

}
