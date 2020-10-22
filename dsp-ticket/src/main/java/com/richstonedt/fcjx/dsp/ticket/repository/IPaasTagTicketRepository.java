package com.richstonedt.fcjx.dsp.ticket.repository;

import com.richstonedt.fcjx.dsp.ticket.pojo.po.PaasTagTicketDO;

import java.util.List;

public interface IPaasTagTicketRepository {

    List<PaasTagTicketDO> findByTag(List<String> tags);
}
