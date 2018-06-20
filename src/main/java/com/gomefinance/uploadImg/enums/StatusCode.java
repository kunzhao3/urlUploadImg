package com.gomefinance.uploadImg.enums;

public enum StatusCode {
	ONLINE_LOAN_0000("0000", "成功"),
    ONLINE_LOAN_0001("0001", "对象属性驼峰转大写下划线异常"),
    ONLINE_LOAN_0002("0002", "http url为空"),
    ONLINE_LOAN_0024("0024", "影像系统文件上传异常"),
    ONLINE_LOAN_0025("0025", "文件服务器文件上传异常"),
    ONLINE_LOAN_0003("0003", "http client调用三方系统异常");
    
    private String code;
    private String msg;

    StatusCode(String code, String showMsg) {
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

    public static StatusCode getByCode(String code) {

        for (StatusCode e : values()) {
            if (e.code.equalsIgnoreCase(code)) {
                return e;
            }
        }
        return null;
    }
}
