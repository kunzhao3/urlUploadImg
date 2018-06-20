package com.gomefinance.uploadImg.domain;

import java.io.Serializable;

public class FileUploadMutilPicReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sysName;// 业务编码
	private String appNo;// 业务单号
	private byte[] files;// 文件
	private String fileName;// 文件名
	private String if_patch_bolt;
	
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public byte[] getFiles() {
		return files;
	}

	public void setFiles(byte[] files) {
		this.files = files;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getIf_patch_bolt() {
		return if_patch_bolt;
	}

	public void setIf_patch_bolt(String if_patch_bolt) {
		this.if_patch_bolt = if_patch_bolt;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "sysName=" + sysName + "|appNo=" + appNo + "|files=" + new String(files) + "|if_patch_bolt="
				+ if_patch_bolt + "|fileName=" + fileName;
	}
}
