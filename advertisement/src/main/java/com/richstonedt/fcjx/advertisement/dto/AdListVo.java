package com.richstonedt.fcjx.advertisement.dto;

import lombok.*;

/**
 * <b><code>AdVo</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 14:54.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdListVo {

    private String adId;
    private String region;
    private String platform;
    private Byte status;

}
