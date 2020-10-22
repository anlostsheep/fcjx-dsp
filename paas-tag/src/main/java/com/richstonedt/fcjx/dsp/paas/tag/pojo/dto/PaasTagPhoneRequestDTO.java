package com.richstonedt.fcjx.dsp.paas.tag.pojo.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagPhoneRequestDTO {

    private List<String> tags;
    private Long pageNum;
    private Long pageSize;
}
