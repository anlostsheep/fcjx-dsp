package com.richstonedt.fcjx.dsp.paas.tag.pojo.bo;

import lombok.*;

import java.util.List;

/**
 * <b><code>PaasTagPageableBO</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/20 5:35 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagPageableBO<T> {

    private Long pageNum;
    private Long pageSize;
    private Long count;
    private List<T> data;
}
