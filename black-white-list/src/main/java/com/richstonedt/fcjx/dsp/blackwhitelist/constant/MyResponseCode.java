package com.richstonedt.fcjx.dsp.blackwhitelist.constant;

/**
 * <b><code>MyResponseCode</code></b>
 * <p/>自定义响应状态码<p/>
 * <b>Creation Time:</b> 2020/1/2 18:40.
 * @author liangqinglong
 * @since developer-environment
 */
public enum MyResponseCode {

    // 成功
    OK(200, "OK"),

    // 服务器端错误
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // 请求超时
    TIME_OUT(504, "请求超时"),

    //  请求参数错误
    INVALID_PARAMETER(400, "无效请求参数"),

    // 默认广告素材响应
    DEFAULT_AD(2001, "没有广告素材，请自行给客户端返回默认广告素材"),

    // 号码在全局黑名单中的响应
    PHONE_NUM_IN_ALL_BLACK_LIST(2002, "号码在全局黑名单中"),

    // 号码在广告素材黑名单中的响应
    PHONE_NUM_IN_AD_BLACK_LIST(2003, "号码在广告素材黑名单中"),

    // 号码在广告素材白名单中的响应
    PHONE_NUM_IN_AD_WHITE_LIST(2004, "号码在广告素材白名单中"),

    // 全部广告素材都下线了的响应
    ALL_ADS_OFFLINE(2005, "所有广告素材都下线了");

    /**
     * 请求响应的状态码
     */
    private int code;
    /**
     * 请求响应消息体
     */
    private String msg;

    MyResponseCode(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
