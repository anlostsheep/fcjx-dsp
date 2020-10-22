package com.richstonedt.fcjx.dsp.paas.tag.pojo.vo;

import lombok.*;

/**
 * <b><code>PaasTagVO</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/17 3:56 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagVO {

    private String name;
    private String alias;
    private Byte status;
}
