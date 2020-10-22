package com.richstonedt.fcjx.dsp.paas.tag.pojo.assember;

import com.richstonedt.fcjx.dsp.paas.tag.pojo.bo.PaasTagPageableBO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.vo.PaasTagPhoneVO;

public class PaasTagPageableBOAssember {

    public static PaasTagPhoneVO bo2vo(PaasTagPageableBO<String> bo) {
        return PaasTagPhoneVO.builder()
                .pageNum(bo.getPageNum())
                .pageSize(bo.getPageSize())
                .count(bo.getCount())
                .phones(bo.getData())
                .build();
    }
}
