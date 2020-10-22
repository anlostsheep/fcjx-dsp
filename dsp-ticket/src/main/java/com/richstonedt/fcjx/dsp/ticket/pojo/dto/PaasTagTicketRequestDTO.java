package com.richstonedt.fcjx.dsp.ticket.pojo.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaasTagTicketRequestDTO {

    private List<String> tags;
}
