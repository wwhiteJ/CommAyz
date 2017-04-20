package com.wwhite.proj.commalyz.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/*
 * ref: http://www.cnblogs.com/zhuawang/archive/2012/12/08/2809380.html
 */
public class HttpUtil {

	public static String sendGet(String url, Map<String,String> headers) {
		
		String result = "";
		
		BufferedReader in = null;
		
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			
			SslUtil.ignoreSsl();
			
			URLConnection connection = realUrl.openConnection();
			if( headers != null ){
				for( String key : headers.keySet() ){
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			connection.setRequestProperty("Connection", "keep-alive");  
			connection.setRequestProperty("Cache-Control", "no-cache"); 
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, Map<String,String> headers, String postData) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			
			SslUtil.ignoreSsl();
			
			URLConnection connection = realUrl.openConnection();
			if( headers != null ){
				for( String key : headers.keySet() ){
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			connection.setRequestProperty("Connection", "keep-alive");  
			connection.setRequestProperty("Cache-Control", "no-cache"); 
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.print(postData);
			out.flush();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
