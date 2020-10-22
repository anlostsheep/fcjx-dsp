package com.richstonedt.fcjx.dsp.ticket.service.impl;

import com.richstonedt.fcjx.dsp.ticket.pojo.assember.PaasTagTicketAssember;
import com.richstonedt.fcjx.dsp.ticket.pojo.dto.PaasTagTicketDTO;
import com.richstonedt.fcjx.dsp.ticket.pojo.po.PaasTagTicketDO;
import com.richstonedt.fcjx.dsp.ticket.repository.IPaasTagTicketRepository;
import com.richstonedt.fcjx.dsp.ticket.service.IPaasTagTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PaasTagTicketServiceImpl implements IPaasTagTicketService {

    @Autowired
    private IPaasTagTicketRepository paasTagTicketRepository;

    @Override
    public List<PaasTagTicketDTO> getOrderByTag(List<String> tags) {
        List<PaasTagTicketDO> ticketDOS = paasTagTicketRepository.findByTag(tags);
        return ticketDOS.stream().map(PaasTagTicketAssember::po2dto).collect(toList());
    }
}
