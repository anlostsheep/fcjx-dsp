package com.richstonedt.fcjx.dsp.paas.tag.controller;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.assember.PaasTagAssember;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.assember.PaasTagPageableBOAssember;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.bo.PaasTagPageableBO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.MyResponseDTO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagDTO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.dto.PaasTagPhoneRequestDTO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.vo.PaasTagPhoneVO;
import com.richstonedt.fcjx.dsp.paas.tag.pojo.vo.PaasTagVO;
import com.richstonedt.fcjx.dsp.paas.tag.service.BaseService;
import com.richstonedt.fcjx.dsp.paas.tag.service.IPaasTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/paas/tag")
@Slf4j
public class PaasTagController {

    private final IPaasTagService paasTagService;

    @Autowired
    private BaseService baseService;

    public PaasTagController(IPaasTagService paasTagService) {
        this.paasTagService = paasTagService;
    }

    @GetMapping("/{phone}")
    public DeferredResult< MyResponseDTO< List<PaasTagVO> > > getTagByPhone(@PathVariable("phone") String phone) {
        Assert.isTrue(!StringUtils.isEmpty(phone), "非法号码");

        return baseService.processRequestAsync(5000L, () -> {
                List<PaasTagDTO> paasTagDTOList = paasTagService.getTagByPhone(phone);
                List<PaasTagVO> orderVOList = paasTagDTOList.stream().map(PaasTagAssember::dto2vo).collect(toList());
            return MyResponseDTO.< List<PaasTagVO> >builder().build().success(orderVOList);
        });
    }

    @PostMapping("/all-in")
    public DeferredResult< MyResponseDTO< List<PaasTagPhoneVO> > > getPhoneByTagAllIn(@RequestBody PaasTagPhoneRequestDTO requestDTO) {
        return getPhoneByTag(requestDTO, () -> paasTagService.getPhoneByTagAllIn(requestDTO.getTags(), requestDTO.getPageNum(), requestDTO.getPageSize()));
    }

    @PostMapping("/any-in")
    public DeferredResult< MyResponseDTO< List<PaasTagPhoneVO> > > getPhoneByTagAnyIn(@RequestBody PaasTagPhoneRequestDTO requestDTO) {
        return getPhoneByTag(requestDTO, () -> paasTagService.getPhoneByTagAnyIn(requestDTO.getTags(), requestDTO.getPageNum(), requestDTO.getPageSize()));
    }

    private DeferredResult< MyResponseDTO< List<PaasTagPhoneVO> > > getPhoneByTag(PaasTagPhoneRequestDTO requestDTO, Supplier<PaasTagPageableBO<String>> supplier) {
        List<String> tags = requestDTO.getTags();
        Assert.notEmpty(tags, "标签不能为空");

        Long pageNum = requestDTO.getPageNum();
        Assert.isTrue(pageNum > 0, "非法页码");

        Long pageSize = requestDTO.getPageSize();
        Assert.isTrue(pageSize > 0 && pageSize <= 500, "非法页数");

        return baseService.processRequestAsync(5000L, () -> {
            PaasTagPageableBO<String> pageableBO = supplier.get();
            PaasTagPhoneVO vo = PaasTagPageableBOAssember.bo2vo(pageableBO);
            return MyResponseDTO.< List<PaasTagPhoneVO> >builder().build().success(Lists.newArrayList(vo) );
        });
    }
}
