package com.example.demo.Response_Message;

public enum RespCode {
    SUCCESS(0, "请求成功"),
    FAILCODE(-1, "请求失败，缺少必要参数"),
    PassToken(0, "您已通过token验证"),
    NameError(-1, "登录失败,用户不存在"),
    PassError(-2, "登录失败,密码错误"),
    WARN1(990, "网络异常，请稍后重试");

    private int code;
    private String msg;


    RespCode(int code, String msg) {
        this.code=code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}
