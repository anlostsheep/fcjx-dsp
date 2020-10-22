package com.richstonedt.fcjx.advertisement.dto;

import lombok.*;

/**
 * <b><code>AdOrderVo</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/8 16:38.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdOrderVo {

    private String tag;

    private Integer ticketNo;

    private Integer status;
}
