package com.gomefinance.uploadImg.util;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.gomefinance.uploadImg.enums.StatusCode;

@Service
public class HttpClientUtil {
	 public String imgFileUpload(String url,Map<String, byte[]> binParams,  Map<String, String> txtParams) throws Exception{
	    	HttpPost post = new HttpPost(url);
	    	MultipartEntityBuilder builder = MultipartEntityBuilder.create();
	    	builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    	
	    	//批量的图片文件
	    	for(Map.Entry<String, byte[]> binParam: binParams.entrySet()){
	    		String key = binParam.getKey();
	    		String name = key.split("\\$")[0];
	    		String fileName = key.split("\\$")[1];
	    		//2进制内容
	    		byte[] bytes = binParam.getValue();
	    		builder.addBinaryBody(name, bytes,ContentType.DEFAULT_BINARY, fileName);
	    	}
	    	
	    	
	    	for(Map.Entry<String, String> txtParam: txtParams.entrySet()){
	    		String key = txtParam.getKey();
	    		//文本内容
	    		String message = txtParam.getValue();
	    	    builder.addTextBody(key, message, ContentType.TEXT_PLAIN);
	    	}
	    	//entity构造
	    	HttpEntity entity = builder.build();
	    	post.setEntity(entity);
	        try {
	            byte[] msgBody = getResult(post);
	            String returnXml = new String(msgBody, "UTF-8").trim();
	            return returnXml;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
	    }
	 
	 private byte[] getResult(HttpRequestBase request) throws Exception{  
	    	CloseableHttpClient httpClient = HttpClients.createDefault();
	    	CloseableHttpResponse response = null;
	        try{ 

	            request.setConfig(getRequestConfig());
	            response = httpClient.execute(request);
	 
	            HttpEntity entity = response.getEntity();
	            byte[] msgBody = getByteArray(entity);
	            return msgBody;
	        }catch(Exception e){  
	            throw e;
	        }finally {
	        	if(response != null){
	        		HttpClientUtils.closeQuietly(response);
	        	}
	            // 关闭连接,释放资源
	            HttpClientUtils.closeQuietly(httpClient);
	        }
	    }  
	   private RequestConfig getRequestConfig(){
	        RequestConfig requestConfig = RequestConfig.custom()  
	                .setConnectTimeout(30000)//设置连接超时时间，单位毫秒。
	                .setConnectionRequestTimeout(5000)//设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
	                .setSocketTimeout(30000)//请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
	                .build(); 
	        return requestConfig;
	    }
	   private byte[] getByteArray(HttpEntity entity) throws Exception{
	    	if (entity != null) {
	            byte[] msgBody = null;
	            InputStream is = entity.getContent();// 获取返回数据
	            byte[] temp = new byte[1024];
	            int n = 0;
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            while ((n = is.read(temp)) != -1) {
	                bos.write(temp, 0, n);
	            }
	            try {
	            	msgBody = bos.toByteArray();
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					if(bos != null) bos.close();
					if(is != null) is.close();
				}
	            return msgBody;
	        }
	    	return null;
	    }
	   
	   /**
		 * 从文件服务器下载文件
		 * @param key 上传文件时返回的文件唯一标识key
		 * @param url
		 * @return 返回一个流
		 */
		public  byte[] fileDownloadServer(String url){
			try {
				HttpGet httpGet = new HttpGet(url);  
				byte[] fileByte  =getResult(httpGet);
				if(fileByte == null){
					throw new Exception("下载图片异常");
				}
				return fileByte;
			} catch (Exception e) {
				throw new BaseException(StatusCode.ONLINE_LOAN_0003.getCode(),StatusCode.ONLINE_LOAN_0003.getMsg(),e);
			}
		}
}
