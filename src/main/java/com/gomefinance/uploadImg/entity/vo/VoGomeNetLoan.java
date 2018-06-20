package com.gomefinance.uploadImg.entity.vo;

import java.io.Serializable;

public class VoGomeNetLoan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 身份证正面照片URL
	 */
    private String frontIdCard;
    /**
     * 身份证背面照片URL
     */
    private String backIdCrad;
    /**
     * 活体识别照片URL
     */
    private String livingPhoto;
    /**
     * 未查询到的编码
     */
    private Integer count;

    public String getFrontIdCard() {
        return frontIdCard;
    }

    public void setFrontIdCard(String frontIdCard) {
        this.frontIdCard = frontIdCard;
    }

    public String getBackIdCrad() {
        return backIdCrad;
    }

    public void setBackIdCrad(String backIdCrad) {
        this.backIdCrad = backIdCrad;
    }

    public String getLivingPhoto() {
        return livingPhoto;
    }

    public void setLivingPhoto(String livingPhoto) {
        this.livingPhoto = livingPhoto;
    }

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
