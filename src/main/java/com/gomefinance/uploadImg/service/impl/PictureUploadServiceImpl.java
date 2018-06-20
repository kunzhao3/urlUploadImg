package com.gomefinance.uploadImg.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gomefinance.uploadImg.common.datasource.DatabaseContextHolder;
import com.gomefinance.uploadImg.common.datasource.DatabaseType;
import com.gomefinance.uploadImg.domain.Picture;
import com.gomefinance.uploadImg.domain.Picupload;
import com.gomefinance.uploadImg.domain.TmAppMain;
import com.gomefinance.uploadImg.entity.vo.VoGomeNetLoan;
import com.gomefinance.uploadImg.mapper.PictureMapper;
import com.gomefinance.uploadImg.mapper.TmAppMainMapper;
import com.gomefinance.uploadImg.service.PictureUploadService;
import com.gomefinance.uploadImg.util.EntryDataHelper;
import com.rock.framework.facade.IFaceImageFacade;
import com.rock.framework.facade.IImageFileFacade;
import com.rock.framework.facade.vo.outupt.UserApiResult;


@Service
public class PictureUploadServiceImpl implements PictureUploadService {
	private static final Logger logger = Logger.getLogger(PictureUploadServiceImpl.class);
    @Value("${create.time}")
    private String createTime;
    @Value("${page.size}")
    private int number;
    @Value("${is.prod}")
    private boolean system;
	
	@Autowired
	private PictureMapper pictureMapper;
	@Autowired
	private TmAppMainMapper TmAppMainMapper;
    @Autowired
    IImageFileFacade iImageFileFacade;
    @Autowired
    IFaceImageFacade iFaceImageFacade;
    @Autowired
    EntryDataHelper entryDataHelper;
	
	@Override
	public void uploadToPicture() {
		logger.info("查询时间createTime在："+createTime+"以前的数据");
		DatabaseContextHolder.setDatabaseType(DatabaseType.jiedb);
		int count =0;
		if(system){
			count=50000;
		}else{
			count =TmAppMainMapper.getAppMainCount(createTime);
		}
		int page=count/number;
		logger.info("查询总条数count="+count+"每页number="+number+"条"+"page="+page);
	    boolean f=true; 
		do {
			for (int i = 0; i <= page; i++) {
				List<TmAppMain> tmList=TmAppMainMapper.getTmAppMain(createTime, i*number, (i+1)*number);
				for (TmAppMain tmAppMain : tmList) {
					DatabaseContextHolder.setDatabaseType(DatabaseType.picdb);
					List<Picture> list =pictureMapper.getPicture(tmAppMain.getAppNo());
					if(list!=null && list.size()>0){
						logger.info("进件单号："+tmAppMain.getAppNo()+"影像系统已有数据");
						//原来就有，无需操作上传，插入数据状态为3
						saveTmPicUpload(tmAppMain.getAppNo(), "3");
						continue;
					}
					//1，调用用户中心查询数据
					VoGomeNetLoan voGomeNetLoan=getWhitebarImg(tmAppMain.getUniqueId());
				    //2，没查询出来，无需操作上传，插入数据状态为0
					if(voGomeNetLoan.getCount()==0){
						logger.info("进件单号："+tmAppMain.getAppNo()+"用户中心无数据");
						saveTmPicUpload(tmAppMain.getAppNo(), "0");
						continue;
					}
					//3，完全查询或者部分查出，上传影像，插入数据状态全部为1，部分为2
					if(voGomeNetLoan.getCount()<3){
						logger.info("进件单号："+tmAppMain.getAppNo()+"用户中心只有一部分影像");
						Map<String, VoGomeNetLoan> dataMap =new HashMap<String, VoGomeNetLoan>();
						dataMap.put("voGomeNetLoan", voGomeNetLoan);
						entryDataHelper.saveFile(dataMap, tmAppMain.getAppNo(), "2");
					}else{
						logger.info("进件单号："+tmAppMain.getAppNo()+"用户中心数据完整");
						Map<String, VoGomeNetLoan> dataMap =new HashMap<String, VoGomeNetLoan>();
						dataMap.put("voGomeNetLoan", voGomeNetLoan);
						entryDataHelper.saveFile(dataMap, tmAppMain.getAppNo(), "1");
					}
				}
			}
			f=false;
		} while (f);
		
	}
	private void  saveTmPicUpload(String appNo,String status){
		Date currentTime = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String nowTime = formatter.format(currentTime); 
		Picupload picupload=new Picupload(appNo, nowTime, nowTime, status);
		DatabaseContextHolder.setDatabaseType(DatabaseType.jiedb);
		TmAppMainMapper.saveTmPicUpload(picupload);
	}
	/**
	 * 通过customerId获取图片地址
	 * @param customerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private VoGomeNetLoan getWhitebarImg(String customerId){
		VoGomeNetLoan voGomeNetLoan=new VoGomeNetLoan();
		Integer count=0;
		//正面
		UserApiResult front=iImageFileFacade.getImageFileByCustomerId(customerId, "F");
		if("0000".equals(front.getCode())){
			Map<String, String> img= (Map<String, String>) front.getData();
			voGomeNetLoan.setFrontIdCard(img.get("imagePath"));
			count++;
		}
		//正面
		UserApiResult reverse=iImageFileFacade.getImageFileByCustomerId(customerId, "R");
		if("0000".equals(reverse.getCode())){
			Map<String, String> img= (Map<String, String>) reverse.getData();
			voGomeNetLoan.setBackIdCrad(img.get("imagePath"));
			count++;
		}
		//活体图片
		UserApiResult face=iFaceImageFacade.queryFaceImageByCustomerId (customerId);
		if("0000".equals(face.getCode())){
			Map<String, String> img= (Map<String, String>) face.getData();
			voGomeNetLoan.setLivingPhoto(img.get("imagePath"));
			count++;
		}
		voGomeNetLoan.setCount(count);
		return voGomeNetLoan;
	}
	
	@Override
	public void checkExists() {
		
	}
}
