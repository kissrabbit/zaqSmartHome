package com.zaq.smartHome.baidu;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.zaq.smartHome.util.AppUtil;
import com.zaq.smartHome.util.BDUtil;
import com.zaq.smartHome.util.HttpPoolUtil;

/**
 * 语音转文字
 * 
 * @author zaqzaq 2015年12月5日
 * 
 */
public class STTutil {
	private static Logger logger=Logger.getLogger(STTutil.class);
	private static final String API_URI = "http://vop.baidu.com/server_api";

	/**
	 * 将fileFormat格式的音频文件soundFile转成文字
	 * @param soundFile
	 * @param fileFormat
	 * @return
	 * @throws Exception
	 */
	public static String done(File soundFile,String fileFormat) throws Exception {
		
		return done(FileUtils.readFileToByteArray(soundFile),fileFormat);
	}
	/**
	 * 默认音频为WAV格式
	 * @param soundFile
	 * @return
	 * @throws Exception
	 */
	public static String done(File soundFile) throws Exception {
		
		return done(FileUtils.readFileToByteArray(soundFile),"wav");
	}
	
	/**
	 * 默认音频为WAV格式
	 * @param soundFile
	 * @return
	 * @throws Exception
	 */
	public static String done(byte[] soundByte) throws Exception {
		
		return done(soundByte,"wav");
	}
	public static String done(byte[] soundByte,String fileFormat) throws Exception {
		logger.debug("请求语识别");
		ByteArrayEntity byteArrayEntity=new ByteArrayEntity(soundByte);
		String httpRes=HttpPoolUtil.postRetStr(API_URI + "?cuid=" + AppUtil.getPropertity("cuid") + "&token=" + BDUtil.getToken(), 
												new BasicHeader("Content-Type", "audio/"+fileFormat+"; rate=8000"),byteArrayEntity);
		logger.debug("请求语识别返回："+httpRes);
		return getText(httpRes);

	}
	
	public static String done(InputStream inputStream) throws Exception {
		
		return done(inputStream,"wav");
	}
	
	public static String done(InputStream inputStream,String fileFormat) throws Exception {
		
		InputStreamEntity inputStreamEntity=new InputStreamEntity(inputStream);
		String httpRes=HttpPoolUtil.postRetStr(API_URI + "?cuid=" + AppUtil.getPropertity("cuid") + "&token=" + BDUtil.getToken(), 
												new BasicHeader("Content-Type", "audio/"+fileFormat+"; rate=8000"),inputStreamEntity);
		logger.debug("请求语识别返回："+httpRes);
		return getText(httpRes);

	}
	
	private static String getText(String httpRes){
		return StringUtils.removeEnd((String) new JSONObject(httpRes).getJSONArray("result").get(0), "，");
	}
	
	@Deprecated
	private static String done1(byte[] soundByte) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(API_URI + "?cuid=" + AppUtil.getPropertity("cuid") + "&token=" + BDUtil.getToken()).openConnection();

		// add request header
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "audio/wav; rate=8000");

		conn.setDoInput(true);
		conn.setDoOutput(true);

		// send request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.write(soundByte);
		wr.flush();
		wr.close();

		return printResponse(conn);
	}
	@Deprecated
	private static void done1(File soundFile) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(API_URI).openConnection();

		// construct params
		JSONObject params = new JSONObject();
		params.put("format", "wav");
		params.put("rate", 8000);
		params.put("channel", "1");
		params.put("token", AppUtil.getPropertity("cuid"));
		params.put("cuid", AppUtil.getPropertity("cuid"));
		params.put("len", soundFile.length());
		params.put("speech", DatatypeConverter.printBase64Binary(FileUtils.readFileToByteArray(soundFile)));

		// add request header
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

		conn.setDoInput(true);
		conn.setDoOutput(true);

		// send request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(params.toString());
		wr.flush();
		wr.close();

		printResponse(conn);
	}

	@Deprecated
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
	
	public static void main(String[] args) throws Exception {
		AppUtil.init();
		File file = new File("sound" + File.separator + "xxx_new.wav");

		String retVal = done(FileUtils.readFileToByteArray(file),"wav");
		System.out.println(retVal);
		System.out.println(new JSONObject(retVal).toString(4));
		System.out.println(new JSONObject(retVal).getJSONArray("result").get(0));
	}
}
