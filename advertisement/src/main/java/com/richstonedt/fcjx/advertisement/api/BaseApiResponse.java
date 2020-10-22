package com.richstonedt.fcjx.advertisement.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b><code>BaseApiResponse</code></b>
 * <p/>
 * 接口基础响应
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 17:39.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseApiResponse<T> {

    private Integer code;
    private String message;
    private T data;
}
