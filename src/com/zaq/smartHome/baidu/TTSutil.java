package com.zaq.smartHome.baidu;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.message.BasicNameValuePair;

import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.BDUtil;
import com.zaq.smartHome.util.HttpPoolUtil;
/**
 * 文字转语音
 * @author zaqzaq
 * 2015年12月5日
 *
 */
public class TTSutil {
	static final String API_URI="http://tsn.baidu.com/text2audio";

    /**
     * 将tex文本转成语音字节
     * @param tex
     * @return
     * @throws Exception
     */
    public static byte[] done(String tex) throws Exception {
        return HttpPoolUtil.postRetMulti(API_URI, new BasicNameValuePair("tex", tex)
								        , new BasicNameValuePair("cuid", AppUtil.getPropertity("cuid"))
								        , new BasicNameValuePair("tok", BDUtil.getToken())
								        , new BasicNameValuePair("lan", "zh")
								        , new BasicNameValuePair("ctp", "1"));
        
    }
    
    public static void main(String[] args) throws Exception {
    	AppUtil.init();
    	byte[] soundByte= done("果果乖，果果乖，果果乖，果果乖，乖乖乖乖，不要哭哦！再哭就要打屁股哦，乖哦！");
    	File file=new File("sound"+File.separator+"test.mp3");
        
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(soundByte);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}
