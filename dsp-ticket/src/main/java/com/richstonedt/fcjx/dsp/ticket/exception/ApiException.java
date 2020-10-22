package com.richstonedt.fcjx.dsp.ticket.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * <b><code>ApiException</code></b>
 * <p/>
 * API异常
 * <p/>
 * <b>Creation Time:</b> 2020/1/2 18:07.
 *
 * @author user
 * @since developer-environment
 */
@Setter
@Getter
public class ApiException extends RuntimeException {

    private String msg;
    private Integer code;
    private Object data;

    public ApiException(Integer code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ApiException(Integer code, String msg, Object data) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
