package com.richstonedt.fcjx.dsp.ticket.pojo.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagTicketDTO {

    private String tag;
    private Integer ticketNo;
    private Byte status;
}
