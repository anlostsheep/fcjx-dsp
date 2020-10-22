package com.richstonedt.fcjx.dsp.ticket.constant;

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
    INVALID_PARAMETER(400, "无效请求参数");

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
