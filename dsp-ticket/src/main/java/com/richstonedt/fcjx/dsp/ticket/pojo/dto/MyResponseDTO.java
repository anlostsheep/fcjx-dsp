package com.richstonedt.fcjx.dsp.ticket.pojo.dto;

import com.richstonedt.fcjx.dsp.ticket.constant.MyResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b><code>MyResponseDTO</code></b>
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
public class MyResponseDTO<T> implements Serializable {

    private Integer code;
    private String desc;
    private T data;

    public MyResponseDTO<T> success(T data) {
        MyResponseCode ok = MyResponseCode.OK;
        return new MyResponseDTO<>(ok.getCode(), ok.getMsg(), data);
    }

    public MyResponseDTO<T> timeout(T data) {
        MyResponseCode timeout = MyResponseCode.TIME_OUT;
        return new MyResponseDTO<>(timeout.getCode(), timeout.getMsg(), data);
    }

    public MyResponseDTO<T> error(T data) {
        MyResponseCode error = MyResponseCode.INTERNAL_SERVER_ERROR;
        return new MyResponseDTO<>(error.getCode(), error.getMsg(), data);
    }
}
