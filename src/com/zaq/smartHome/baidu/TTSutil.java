package com.zaq.smartHome.baidu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.DatatypeConverter;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

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
	static final String URI="http://tsn.baidu.com/text2audio";

    public static void main(String[] args) throws Exception {
    	AppUtil.init();
        method3();
    }

    
    private static void method3() throws Exception {
        byte[] soundByte= HttpPoolUtil.postRetMulti(URI, new BasicNameValuePair("tex", "xxxx")
								        , new BasicNameValuePair("cuid", AppUtil.getPropertity("cuid"))
								        , new BasicNameValuePair("tok", BDUtil.getToken())
								        , new BasicNameValuePair("lan", "zh")
								        , new BasicNameValuePair("ctp", "1"));
        
        
        File file=new File("mp3"+File.separator+"test.mp3");
        
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(soundByte);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}
