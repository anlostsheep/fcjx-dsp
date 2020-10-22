package com.richstonedt.fcjx.advertisement.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <b><code>SyncAdResponse</code></b>
 * <p/>
 * 哇棒-丰石广告同步响应
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 16:00.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncAdResponse {

    private String resultStatus;

    private String errorInfo;

    private Integer httpCode;
}
