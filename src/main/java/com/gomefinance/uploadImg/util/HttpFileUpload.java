package com.gomefinance.uploadImg.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.gomefinance.uploadImg.domain.FileUploadMutilPicReq;
import com.gomefinance.uploadImg.domain.FileUploadMutilPicRsp;
import com.gomefinance.uploadImg.enums.StatusCode;

public class HttpFileUpload {
	private static final Logger logger = Logger.getLogger(HttpFileUpload.class);
	private static final String IF_PATCH_BOLT = "Y";
	private static final String IMAGE_UPLOAD_MUTILPIC_SUCCESS = "S";
	
	public static FileUploadMutilPicRsp fileUploadMutilPic(FileUploadMutilPicReq req,String url,String imageType) throws BaseException{
		try {
			String if_patch_bolt = req.getIf_patch_bolt() !=null ? req.getIf_patch_bolt() : IF_PATCH_BOLT;
	        Map<String, byte[]> binParam = new HashMap<String, byte[]>();
	        binParam.put("files$" + imageType+"_"+req.getFileName(), req.getFiles());
	        Map<String, String> txtParams = new HashMap<String, String>();
	        txtParams.put("if_patch_bolt", if_patch_bolt);
	        txtParams.put("appNo", req.getAppNo());
	        txtParams.put("sysName","mj");
	        //TODO 配置文件系统url
	        logger.info("进件单号"+req.getAppNo()+"请求镜像地址"+url);
	        String result = new HttpClientUtil().imgFileUpload(url, binParam, txtParams);
	        logger.info("进件单号"+req.getAppNo()+"响应结果"+result);
	        FileUploadMutilPicRsp rsp = JSON.parseObject(result, FileUploadMutilPicRsp.class);
	        if (rsp == null || !IMAGE_UPLOAD_MUTILPIC_SUCCESS.equals(rsp.getCODE())) {
	            throw new BaseException(StatusCode.ONLINE_LOAN_0024);
	        }      
	        return rsp;
		} catch (Exception e) {
			throw new BaseException(StatusCode.ONLINE_LOAN_0003.getCode(), StatusCode.ONLINE_LOAN_0003.getMsg(), e);
		}
	}
}
