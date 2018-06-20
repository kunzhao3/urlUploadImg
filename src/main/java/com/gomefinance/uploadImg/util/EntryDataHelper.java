package com.gomefinance.uploadImg.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gomefinance.uploadImg.common.datasource.DatabaseContextHolder;
import com.gomefinance.uploadImg.common.datasource.DatabaseType;
import com.gomefinance.uploadImg.domain.FileUploadMutilPicReq;
import com.gomefinance.uploadImg.domain.FileUploadMutilPicRsp;
import com.gomefinance.uploadImg.domain.Picture;
import com.gomefinance.uploadImg.domain.Picupload;
import com.gomefinance.uploadImg.entity.vo.VoGomeNetLoan;
import com.gomefinance.uploadImg.enums.MakeUpFilesTypeEnum;
import com.gomefinance.uploadImg.enums.StatusCode;
import com.gomefinance.uploadImg.mapper.PictureMapper;
import com.gomefinance.uploadImg.mapper.TmAppMainMapper;
import com.gomefinance.uploadImg.service.HttpFileService;

@Component
public class EntryDataHelper {
	private static final Logger logger = Logger.getLogger(EntryDataHelper.class);
	 @Autowired
	 private HttpFileService httpFileService;
	 @Autowired
     private TmAppMainMapper TmAppMainMapper;
	 @Autowired
	 private PictureMapper pictureMapper;
	 private static final String IMAGE_UPLOAD_MUTILPIC_SUCCESS = "S";
	 @Value("${pic.upLoadUrl}")
	 private String upLoadUrl;
	
	 public boolean saveFile(Map<String, VoGomeNetLoan> dataMap,String appNo,String status) {
		if(dataMap != null && dataMap.size() > 0) {
			for (Entry<String, VoGomeNetLoan> key : dataMap.entrySet()) {
				VoGomeNetLoan bank = key.getValue();
				if(bank == null) {
					logger.info("【文件下载】文件内容不符合下载条件！！！");
					return false;
				}
				String[] uri = {bank.getFrontIdCard(), bank.getBackIdCrad(), bank.getLivingPhoto()};
				for (int i = 0; i < uri.length; i++) {
					if(!StringUtils.isEmpty(uri[i])) {
						if(i < 3) {
							if(i == 2) {
								String[] uris = uri[i].split(",");
								for (int j = 0; j < uris.length; j++) {
									if(!StringUtils.isEmpty(uris[j])){
										saveOrUpPicUpload(appNo, uris[j], i, j, status);
									}
								}
									
							} else {
								saveOrUpPicUpload(appNo, uri[i], i, -1, status);
							}
						}
					}
				}
			}
		}
		return false;
	}
	 /**
	  * 上传图片到影像
	  * @param appNo 进件单号
	  * @param url  原图片地址
	  * @param nameType 图片类型
	  * @param faceType face++图片
	  * @return
	  */
	private boolean upLoadPic(String appNo,String url,int nameType,int faceType){
		DatabaseContextHolder.setDatabaseType(DatabaseType.picdb);
		String imageType=getImageType(nameType, faceType);
		//根据图片类型和进件单号：查询影像
		Picture picture=pictureMapper.getPictureByAppNoAndSubClassSort(appNo, imageType);
		if(null==picture){
			byte[] imageByte = new HttpClientUtil().fileDownloadServer(url);
			FileUploadMutilPicReq req = new FileUploadMutilPicReq();
			req.setAppNo(appNo);
			req.setFiles(imageByte);
			req.setFileName(new Date().getTime()+".jpg");
			try {
				FileUploadMutilPicRsp rsp =HttpFileUpload.fileUploadMutilPic(req, upLoadUrl,imageType);
				if(rsp!= null && IMAGE_UPLOAD_MUTILPIC_SUCCESS.equals(rsp.getCODE()) && StringUtils.isNotEmpty(rsp.getFILD_ID())){
					logger.info("进件单号："+appNo+"响应"+rsp.toString());
					return true;
				}
			} catch (BaseException e) {
				logger.info("进件单号："+appNo+"异常"+StatusCode.ONLINE_LOAN_0024.getMsg());
				return false;
			}
		}
		logger.info("进件单号："+appNo+"已经有"+imageType+"类型的图片");
		return false;
	}
	/**
	 * 入征审的数据库保存数据
	 * @param appNo
	 * @param url
	 * @param i
	 * @param j
	 * @param status
	 * @return
	 */
	private int saveOrUpPicUpload(String appNo,String url,int i,int j,String status){
		HttpResponse response = httpFileService.sendGet(url);
		if(response != null){
			boolean f= upLoadPic(appNo, url,i,j);
			if(f){
				String imginfo=getImageType(i, j);
				Date currentTime = new Date();  
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			    String nowTime = formatter.format(currentTime); 
			    DatabaseContextHolder.setDatabaseType(DatabaseType.jiedb);
			    Picupload picupload=TmAppMainMapper.getPicuploadByAppNo(appNo);
				if(null!=picupload){
					picupload.setUpdateTime(nowTime);
					picupload.setImginfo(picupload.getImginfo()+","+imginfo);
					return TmAppMainMapper.upTmPicUpload(picupload);
				}
				picupload=new Picupload(appNo, nowTime, nowTime, status,imginfo);
				return TmAppMainMapper.saveTmPicUpload(picupload);
			}
		}
		return 0;
	}
	/**
	 * 获取图片类别
	 * @param nameType
	 * @param faceType
	 * @return
	 */
	private String getImageType(int  nameType,int faceType){
		String imageType="";
		switch (nameType) {
		case 0:
			imageType= MakeUpFilesTypeEnum.CARDPHOTO_POSITIVE.getCode();
			break;
        case 1:
        	imageType= MakeUpFilesTypeEnum.CARDPHOTO_NEGATIVE.getCode();
			break;
        case 2:
        	if(faceType==0){
        		imageType= MakeUpFilesTypeEnum.CARDPHOTO_ADD1.getCode();
        	}else if (faceType==1){
        		imageType= MakeUpFilesTypeEnum.CARDPHOTO_ADD2.getCode();
        	}else {
        		imageType= MakeUpFilesTypeEnum.CARDPHOTO_ADD3.getCode();
        	}
			break;
		default:
			break;
		}
		return imageType;
	}
}
