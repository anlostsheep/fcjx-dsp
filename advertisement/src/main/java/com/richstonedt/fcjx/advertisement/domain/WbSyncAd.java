package com.richstonedt.fcjx.advertisement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <b><code>AdPhonePackage</code></b>
 * <p/>
 * 哇棒-丰石同步广告
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 17:47.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WbSyncAd {

    private String url;

    @NotBlank(message = "广告 id 不能为空")
    private String advId;

    private String method;

    @NotBlank(message = "广告同步操作不能为空")
    private String opType;

    @NotBlank(message = "渠道名称不能为空")
    private String platform;

    private String[] region;
}
