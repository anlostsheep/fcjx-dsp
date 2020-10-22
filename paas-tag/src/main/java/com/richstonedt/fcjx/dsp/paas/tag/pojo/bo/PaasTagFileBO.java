package com.richstonedt.fcjx.dsp.paas.tag.pojo.bo;

import lombok.*;

import java.util.List;

/**
 * <b><code>PaasTagFileBO</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 3:39 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagFileBO {

    private String tag;
    private List<String> phone;
}
