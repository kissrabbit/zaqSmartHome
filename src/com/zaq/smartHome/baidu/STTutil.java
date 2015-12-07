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
import java.net.URLEncoder;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.BDUtil;

/**
 * 语音转文字
 * @author zaqzaq
 * 2015年12月5日
 *
 */
public class STTutil {

    private static final String serverURL = "http://vop.baidu.com/server_api";

    public static void main(String[] args) throws Exception {
    	AppUtil.init();
    	File file=new File("mp3"+File.separator+"xxx_new.wav");
        
    	String retVal=done(FileUtils.readFileToByteArray(file));
    	
    	System.out.println(retVal);
    }
    private static String done(byte[] mp3Byte) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                + "?cuid=" + AppUtil.getPropertity("cuid") + "&token=" + BDUtil.getToken()).openConnection();

        // add request header
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "audio/wav; rate=8000");

        conn.setDoInput(true);
        conn.setDoOutput(true);

        // send request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(mp3Byte);
        wr.flush();
        wr.close();

       return  printResponse(conn);
    }
    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        System.out.println(new JSONObject(response.toString()).toString(4));
        return response.toString();
    }
//    private static void method1() throws Exception {
//        File pcmFile = new File(testFileName);
//        HttpURLConnection conn = (HttpURLConnection) new URL(serverURL).openConnection();
//
//        // construct params
//        JSONObject params = new JSONObject();
//        params.put("format", "pcm");
//        params.put("rate", 8000);
//        params.put("channel", "1");
//        params.put("token", token);
//        params.put("cuid", cuid);
//        params.put("len", pcmFile.length());
//        params.put("speech", DatatypeConverter.printBase64Binary(loadFile(pcmFile)));
//
//        // add request header
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//
//        // send request
//        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//        wr.writeBytes(params.toString());
//        wr.flush();
//        wr.close();
//
//        printResponse(conn);
//    }

}
