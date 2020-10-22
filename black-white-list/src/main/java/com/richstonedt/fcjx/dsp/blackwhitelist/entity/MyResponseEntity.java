package com.richstonedt.fcjx.dsp.blackwhitelist.entity;

import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b><code>MyResponseEntity</code></b>
 * <p/>
 * 响应实体
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 17:27.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyResponseEntity<T> implements Serializable {

    private Integer code;
    private String desc;
    private T data;

    public MyResponseEntity<T> success(T data) {
        MyResponseCode ok = MyResponseCode.OK;
        return new MyResponseEntity<>(ok.getCode(), ok.getMsg(), data);
    }

    public MyResponseEntity<T> timeout(T data) {
        MyResponseCode timeout = MyResponseCode.TIME_OUT;
        return new MyResponseEntity<>(timeout.getCode(), timeout.getMsg(), data);
    }

    public MyResponseEntity<T> error(T data) {
        MyResponseCode error = MyResponseCode.INTERNAL_SERVER_ERROR;
        return new MyResponseEntity<>(error.getCode(), error.getMsg(), data);
    }
}
