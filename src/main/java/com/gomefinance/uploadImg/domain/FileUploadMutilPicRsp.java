package com.gomefinance.uploadImg.domain;

import java.io.Serializable;

public class FileUploadMutilPicRsp implements Serializable{
	private static final long serialVersionUID = 1L;
	private String CODE;//应答码 0成功
	private String MESSAGE;//应答信息
	private String FILD_ID;//以逗号分隔的字符串
	private String IDs;//以逗号分隔的影像主键字符串
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String cODE) {
		CODE = cODE;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public String getFILD_ID() {
		return FILD_ID;
	}
	public void setFILD_ID(String fILD_ID) {
		FILD_ID = fILD_ID;
	}
	public String getIDs() {
		return IDs;
	}
	public void setIDs(String iDs) {
		IDs = iDs;
	}
	@Override
	public String toString() {
		return "CODE=" + CODE + "|MESSAGE=" + MESSAGE +  "|FILD_ID=" + FILD_ID + "|IDs=" + IDs;
	}
}
