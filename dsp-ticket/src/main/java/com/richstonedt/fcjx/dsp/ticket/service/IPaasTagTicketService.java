package com.richstonedt.fcjx.dsp.ticket.service;

import com.richstonedt.fcjx.dsp.ticket.pojo.dto.PaasTagTicketDTO;

import java.util.List;

public interface IPaasTagTicketService {

    /**
     * 通过标签获取工单
     * @param tags
     * @return
     */
    List<PaasTagTicketDTO> getOrderByTag(List<String> tags);
}
