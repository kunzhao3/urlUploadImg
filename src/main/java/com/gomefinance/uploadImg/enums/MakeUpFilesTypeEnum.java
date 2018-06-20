package com.gomefinance.uploadImg.enums;

public enum MakeUpFilesTypeEnum {
	    CARDPHOTO_POSITIVE("B1", "身份证正面"),
	    CARDPHOTO_NEGATIVE("B2", "身份证反面"),
	    CARDPHOTO_HAND_POSITIVE("B3", "手持身份证照"),
	    CARDPHOTO_ENV("ENV", "环境照片"),
	    CARDPHOTO_ADD1("FACE1", "ref1"),
	    CARDPHOTO_ADD2("FACE2", "ref2"),
	    CARDPHOTO_ADD3("FACE3", "ref3"),
	    CARDPHOTO_ADD4("FACE4", "ref4");

	    private String code;
	    private String msg;

	    MakeUpFilesTypeEnum(String code, String showMsg) {
	        this.code = code;
	        this.msg = showMsg;
	    }

	    public String getMsg() {
	        return msg;
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code;
	    }

	    public void setMsg(String msg) {
	        this.msg = msg;
	    }
}
