package com.richstonedt.fcjx.dsp.ticket.pojo.assember;

import com.richstonedt.fcjx.dsp.ticket.pojo.dto.PaasTagTicketDTO;
import com.richstonedt.fcjx.dsp.ticket.pojo.po.PaasTagTicketDO;
import com.richstonedt.fcjx.dsp.ticket.pojo.vo.PaasTagTicketVO;

public class PaasTagTicketAssember {

    public static PaasTagTicketDTO po2dto(PaasTagTicketDO po) {
        return PaasTagTicketDTO.builder()
                .ticketNo(po.getTicketNo())
                .tag(po.getPaasTag())
                .status(po.getStatus())
                .build();
    }

    public static PaasTagTicketVO dto2vo(PaasTagTicketDTO dto) {
        return PaasTagTicketVO.builder()
                .ticketNo(dto.getTicketNo())
                .tag(dto.getTag())
                .status(dto.getStatus())
                .build();
    }
}
