package com.gomefinance.uploadImg.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gomefinance.uploadImg.service.HttpFileService;


@Service
public class HttpFileServiceImpl implements HttpFileService {
	private static final Logger logger = Logger.getLogger(HttpFileServiceImpl.class);

	@Override
	public HttpResponse sendGet(String uri) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet(uri);
			HttpResponse response = httpClient.execute(get);
			// 获取并验证执行结果
			int code = response.getStatusLine().getStatusCode();
			if(code == HttpStatus.SC_OK){
				return response;
			}else {
				logger.info("请求用户中心失败, url="+uri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	@Override
	public String  sendPost(String uri, String json) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault(); 
			HttpPost httpPost = new HttpPost(uri);  
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");  
			
			StringEntity se = new StringEntity(json, "UTF-8");  
			se.setContentType("text/json");  
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));  
			httpPost.setEntity(se);  
			HttpResponse response = httpClient.execute(httpPost);  
			int code = response.getStatusLine().getStatusCode();
			if(code != HttpStatus.SC_OK) {
				logger.info("请求影像系统异常："+json);
				return null;
			} else {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String jsonStr = EntityUtils.toString(entity, "UTF-8");
					logger.info("影像系统返回: " + jsonStr);
					return jsonStr;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}
}
