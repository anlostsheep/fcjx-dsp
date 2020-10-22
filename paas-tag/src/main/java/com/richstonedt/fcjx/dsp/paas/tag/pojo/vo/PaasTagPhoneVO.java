package com.richstonedt.fcjx.dsp.paas.tag.pojo.vo;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagPhoneVO {

    private Long pageNum;
    private Long pageSize;
    private Long count;
    private List<String> phones;
}
