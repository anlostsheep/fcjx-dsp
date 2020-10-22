package com.richstonedt.fcjx.dsp.paas.tag.pojo.assember;

import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagDTO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.po.TagDO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.vo.PaasTagVO;

/**
 * <b><code>PaasTagAssember</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/16 4:15 下午.
 *
 * @author LIANG QING LONG
 * @since fcjx-dsp
 */
public class PaasTagAssember {

    public static PaasTagDTO po2Dto(TagDO po) {
        return PaasTagDTO.builder()
                .name(po.getName())
                .alias(po.getAlias())
                .status(po.getStatus())
                .build();
    }

    public static PaasTagVO dto2vo(PaasTagDTO dto) {
        return PaasTagVO.builder()
                .name(dto.getName())
                .alias(dto.getAlias())
                .status(dto.getStatus())
                .build();
    }
}
