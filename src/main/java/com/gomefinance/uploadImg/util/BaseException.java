package com.gomefinance.uploadImg.util;

import com.gomefinance.uploadImg.enums.StatusCode;

public class BaseException extends RuntimeException{
    
    private static final long serialVersionUID = -4804050558661709827L;
    
    private String code;
    private String msg;
    private String showMsg;
    private Throwable e;
    
    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }
    
    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    
    public BaseException(StatusCode statusCode){
    	super(statusCode.getMsg());
    	this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }
    
    public BaseException(String code, String msg, String showMsg) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
    }
    
    public BaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.e = e;
    }
    
    public BaseException(String code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
        this.e = e;
    }
    
    public BaseException(String code, String msg, String showMsg, Throwable e) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
        this.showMsg = showMsg;
        this.e = e;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public Throwable getE() {
        return e;
    }
    public void setE(Throwable e) {
        this.e = e;
    }

}
