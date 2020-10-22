package com.richstonedt.fcjx.advertisement.exception;

import com.richstonedt.fcjx.advertisement.contants.ApiEnum;

/**
 * <b><code>SmartPushException</code></b>
 * <p/>
 * 自定义异常类
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 17:48.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public class SmartPushException extends RuntimeException {

    private static final long serialVersionUID = -4442103770240440941L;
    
    private Integer code;

    public SmartPushException() {
        super();
    }

    public SmartPushException(ApiEnum apiEnum) {
        super(apiEnum.getMessage());
        this.code = apiEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
    
