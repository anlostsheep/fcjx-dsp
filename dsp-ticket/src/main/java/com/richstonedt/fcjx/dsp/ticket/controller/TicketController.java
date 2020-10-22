package com.richstonedt.fcjx.dsp.ticket.controller;

import com.richstonedt.fcjx.dsp.ticket.pojo.assember.PaasTagTicketAssember;
import com.richstonedt.fcjx.dsp.ticket.pojo.dto.MyResponseDTO;
import com.richstonedt.fcjx.dsp.ticket.pojo.dto.PaasTagTicketDTO;
import com.richstonedt.fcjx.dsp.ticket.pojo.dto.PaasTagTicketRequestDTO;
import com.richstonedt.fcjx.dsp.ticket.pojo.vo.PaasTagTicketVO;
import com.richstonedt.fcjx.dsp.ticket.service.IPaasTagTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private IPaasTagTicketService paasTagTicketService;

    @PostMapping("/paas-tag")
    public MyResponseDTO< List<PaasTagTicketVO> > getTicketByPaasTag(@RequestBody PaasTagTicketRequestDTO requestDTO) {
        List<String> tags = requestDTO.getTags();
        Assert.notEmpty(tags, "标签不能为空");
        List<PaasTagTicketDTO> tagTicketDTOS = paasTagTicketService.getOrderByTag(tags);
        List<PaasTagTicketVO> tagTicketVOS = tagTicketDTOS.stream().map(PaasTagTicketAssember::dto2vo).collect(toList());
        MyResponseDTO<List<PaasTagTicketVO>> response = MyResponseDTO.<List<PaasTagTicketVO>>builder().build();
        return response.success(tagTicketVOS);
    }
}
