package com.richstonedt.fcjx.advertisement.dto;

import lombok.*;

import java.util.List;

/**
 * <b><code>AdOrderDto</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/8 16:39.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdOrderDto {

    private Integer code;
    private String desc;
    private List<AdOrderVo> data;
}
