package com.richstonedt.fcjx.dsp.paas.tag.pojo.dto;

import lombok.*;

/**
 * <b><code>PaasTagDTO</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 4:13 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagDTO {

    private String name;
    private String alias;
    private Byte status;
}
