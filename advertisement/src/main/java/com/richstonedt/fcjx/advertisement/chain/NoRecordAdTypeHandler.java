package com.richstonedt.fcjx.advertisement.chain;

import com.richstonedt.fcjx.advertisement.contants.ApiContants;
import com.richstonedt.fcjx.advertisement.domain.Ad;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.dto.*;
import com.richstonedt.fcjx.advertisement.service.AdService;
import com.richstonedt.fcjx.advertisement.service.TagOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <b><code>NoRecordHandler</code></b>
 * <p/>
 * 丰石广告数据库中没有记录的广告获取
 * <p/>
 * <b>Creation Time:</b> 2020/4/30 11:51.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("noRecordAdType")
@Slf4j
public class NoRecordAdTypeHandler extends AbstractAdTypeHandler {

    private AdService adService;

    private TagOrderService tagOrderService;

    @Autowired
    public void setAdService(AdService adService) {
        this.adService = adService;
    }

    @Autowired
    public void setTagOrderService(TagOrderService tagOrderService) {
        this.tagOrderService = tagOrderService;
    }

    @Override
    public AdResponse handleAdType(AdRequest adRequest, AdListDto adListDto, String channel) {
        log.info("丰石数据库中没有可匹配的广告处理...");

        if (ApiContants.NO_AD_RECORD_CODE == adListDto.getCode()) {
            return matchTagAds(adRequest, channel);
        }

        return nextHandler.handleAdType(adRequest, adListDto, channel);
    }

    public AdResponse matchTagAds(AdRequest adRequest, String channel) {
        AdResponse wbAds = adService.getWbAds(adRequest, channel);

        if (ApiContants.NO_AD_SHOW_CODE == wbAds.getCode()) {
            return wbAds;
        }

        String correctPhoneNum = getCorrectPhoneNum(adRequest.getDevice().getPhoneNum());

        AdTagDto tagDto = tagOrderService.getTagInfo(correctPhoneNum);

        if (tagDto.getData().isEmpty()) {
            log.info("当前号码[{}]没有标签信息", correctPhoneNum);
            return adService.getNoMatchAds(adRequest.getRequestId());
        }

        List<Ad> ads = wbAds.getAds();

        List<String> tags = tagDto.getData()
                .stream()
                .map(TagVo::getName)
                .collect(Collectors.toList());

        AdOrderDto orderDto = tagOrderService.getOrderInfo(
                Tags.builder()
                        .tags(tags)
                        .build());

        if (orderDto.getData().isEmpty()) {
            log.info("当前号码[{}]没有工单信息",correctPhoneNum);
            return adService.getNoMatchAds(adRequest.getRequestId());
        }

        List<String> orderIds = orderDto.getData()
                .stream()
                .map(adOrderVo -> String.valueOf(adOrderVo.getTicketNo()))
                .collect(Collectors.toList());

        List<Ad> matchOrderIdAds = ads.stream()
                .filter(a -> orderIds.contains(a.getOrderId()))
                .filter(b -> b.getAdWidth().equals(adRequest.getAd().getWidth()))
                .filter(c -> c.getAdHeight().equals(adRequest.getAd().getHeight()))
                .collect(Collectors.toList());

        if (matchOrderIdAds.size() == 0) {
            log.info("当前号码[{}]没有匹配的工单", correctPhoneNum);
            return adService.getNoMatchAds(adRequest.getRequestId());
        }

        wbAds.setAds(matchOrderIdAds);

        return wbAds;
    }
}

