package com.richstonedt.fcjx.advertisement.contants;

/**
 * <b><code>ApiEnum</code></b>
 * <p/>
 * 状态枚举
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 17:43.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public enum ApiEnum {
    /**
     * 成功
     */
    SUCCESS(200, "请求成功"),
    /**
     * 未知的请求
     */
    BAD_REQUEST(400, "未知的请求"),
    /**
     * 请求参数格式有误
     */
    BAD_REQUEST_PARAM(401, "请求参数格式不正确"),
    /**
     * 没有此种广告尺寸
     */
    NO_AD_SIZE(401, "没有此种广告尺寸"),
    /**
     * 内部错误
     */
    INTERNAL_ERROR(500, "服务器出错"),
    /**
     * 请求超时
     */
    GATEWAY_TIMEOUT(504, "网络请求拥挤"),
    /**
     * 全局黑名单
     */
    GLOBAL_BLACK_LIST_AD(4004, "该用户在投诉列表中"),
    /**
     * 没有广告
     */
    NO_MATCH_AD_CODE(4004, "This is no match ad!");


    private Integer code;
    private String message;

    ApiEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
