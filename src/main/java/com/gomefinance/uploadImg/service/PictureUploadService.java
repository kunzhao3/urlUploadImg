package com.gomefinance.uploadImg.service;


public interface PictureUploadService {
	/**
	 * 上传文件
	 */
	void uploadToPicture();
	/**
	 * 检查文件是否上传成功,否则重新上传
	 */
	void checkExists();
	
}
