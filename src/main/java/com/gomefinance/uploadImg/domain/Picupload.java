package com.gomefinance.uploadImg.domain;

public class Picupload {
	private String appNo;

	private String createTime;

	private String updateTime;

	private String status;
	
	private String imginfo;

	public Picupload() {
		
	}

	public Picupload(String appNo, String createTime, String updateTime,
			String status) {
		this.appNo = appNo;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
	}

	public Picupload(String appNo, String createTime, String updateTime,
			String status, String imginfo) {
		this.appNo = appNo;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.imginfo = imginfo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImginfo() {
		return imginfo;
	}

	public void setImginfo(String imginfo) {
		this.imginfo = imginfo;
	}

	@Override
	public String toString() {
		return "Picupload [appNo=" + appNo + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", status=" + status
				+ ", imginfo=" + imginfo + "]";
	}
}
