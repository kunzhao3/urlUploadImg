package com.gomefinance.http;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.gomefinance.uploadImg.domain.FileUploadMutilPicReq;
import com.gomefinance.uploadImg.domain.FileUploadMutilPicRsp;
import com.gomefinance.uploadImg.enums.MakeUpFilesTypeEnum;
import com.gomefinance.uploadImg.util.Base64Utils;
import com.gomefinance.uploadImg.util.HttpClientUtil;
import com.gomefinance.uploadImg.util.HttpFileUpload;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FaceDetectServiceTest {
	
	/**
	 *上传文件系统测试
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void imageUploadTest() throws Exception {

		String FILE_SERVERS_URL = "http://10.152.17.88:8080/fastdfs-gateway/mj/upload";
		String imagePath = "E://file//FACE1_1529035331642.jpg";
		String image = Base64Utils.imageToBase64(new File(imagePath));
		String fileName = "FACE1_1529035331642.jpg";
		// 请求文件服务器保存图片
		System.out.println("开始上传face++图片到文件服务器");
		Map<String, byte[]> binParam = new HashMap<String, byte[]>();
		byte[] bytes = Base64Utils.decode(image);
		binParam.put("file1$" + fileName, bytes);
		Map<String, String> txtParams = new HashMap<String, String>();
		txtParams.put("isStatic", "0");
		// TODO 配置文件系统url
		String result = new HttpClientUtil().imgFileUpload(FILE_SERVERS_URL,
				binParam, txtParams);
		System.out.println("face++上传文件服务器result=" + result);
		Map<String, Object> maps = (Map<String, Object>) JSON.parseObject(
				result, Map.class);
		if (!"000000".equals(maps.get("state"))) {
			throw new Exception("请求文件服务器异常");
		}
		List<Map<String, String>> repList = (List<Map<String, String>>) maps
				.get("body"); // 获取图片url
		System.out.println(repList.get(0).get("path"));
	}
	/**
	 * 上传影像测试
	 */
	@Test
	public void fileUploadMutilPic(){
		//影像地址
		String url = "http://10.152.17.77:10014/pic-app/picture/restMutilPic";
		//用户中心图片地址
		String FILE_SERVERS_DOWNLOAD_URL="http://10.143.92.200/fastdfs-gateway/cif/download?curSysNo=0901&key=921195792482975744&type=0";
		byte[] imageByte1 = new HttpClientUtil().fileDownloadServer(FILE_SERVERS_DOWNLOAD_URL);
		FileUploadMutilPicReq req = new FileUploadMutilPicReq();
		req.setAppNo("MJ201805022152893140");
		req.setFiles(imageByte1);
		req.setFileName("123456789.jpg");
		//最后生成的文件名称  FACE1_123456789.jpg
		FileUploadMutilPicRsp rsp = HttpFileUpload.fileUploadMutilPic(req, url,MakeUpFilesTypeEnum.CARDPHOTO_ADD1.getCode());
		
		System.out.println("返回结果"+rsp);
	}

}
