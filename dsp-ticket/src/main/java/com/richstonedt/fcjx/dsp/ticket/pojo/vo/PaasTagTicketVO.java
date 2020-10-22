package com.richstonedt.fcjx.dsp.ticket.pojo.vo;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagTicketVO {

    private String tag;
    private Integer ticketNo;
    private Byte status;
}
