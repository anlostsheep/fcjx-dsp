package com.richstonedt.fcjx.advertisement.domain;

import lombok.*;

/**
 * <b><code>MobileLocal</code></b>
 * <p/>
 * 号码归属信息
 * <p/>
 * <b>Creation Time:</b> 2020/4/2 15:26.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MobileLocal {

    private Long id;

    private String numberSegment;

    private String area;

    private String areaAlias;

    private String numberType;

    private String areaCode;

    private String postCode;
}
