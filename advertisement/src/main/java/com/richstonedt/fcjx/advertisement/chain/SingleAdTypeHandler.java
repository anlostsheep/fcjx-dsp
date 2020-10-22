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
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <b><code>SingleAdTypeHandler</code></b>
 * <p/>
 * 黑名单/白名单/默认广告类型获取
 * <p/>
 * <b>Creation Time:</b> 2020/4/30 23:21.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("singleAdType")
@Slf4j
public class SingleAdTypeHandler extends AbstractAdTypeHandler {

    private AdService adService;

    private TagOrderService tagOrderService;

    @Autowired
    public void setTagOrderService(TagOrderService tagOrderService) {
        this.tagOrderService = tagOrderService;
    }

    @Autowired
    public void setAdService(AdService adService) {
        this.adService = adService;
    }

    @Override
    public AdResponse handleAdType(AdRequest adRequest, AdListDto adListDto, String channel) {
        log.info("命中单个类型广告处理...");

        AdResponse matchListAds = matchListAds(adRequest, adListDto, channel);

        return matchTagAds(matchListAds,
                getCorrectPhoneNum(adRequest.getDevice().getPhoneNum()),
                adRequest.getRequestId());
    }

    private AdResponse matchListAds(AdRequest adRequest, AdListDto adListDto, String channel) {
        AdResponse wbAds = adService.getWbAds(adRequest, channel);

        if (ApiContants.NO_AD_SHOW_CODE == wbAds.getCode()) {
            log.info("当前尺寸宽:{},高:{},哇棒没有在线广告", adRequest.getAd().getWidth(), adRequest.getAd().getHeight());
            return wbAds;
        }

        if (CollectionUtils.isEmpty(adListDto.getData())) {
            log.info("当前号码[{}]没有返回广告记录", adRequest.getDevice().getPhoneNum());
            return adService.getNoMatchAds(adRequest.getRequestId());
        }

        List<String> adIds = adListDto.getData()
                .stream()
                .map(AdListVo::getAdId)
                .collect(Collectors.toList());

        List<Ad> matchListAds = wbAds.getAds()
                .stream()
                .filter(a -> adIds.contains(a.getAdId()))
                .filter(b -> b.getAdWidth().equals(adRequest.getAd().getWidth()))
                .filter(c -> c.getAdHeight().equals(adRequest.getAd().getHeight()))
                .collect(Collectors.toList());

        if (matchListAds.size() == 0) {
            log.info("当前号码[{}]没有匹配的广告集合", adRequest.getDevice().getPhoneNum());
            return adService.getNoMatchAds(adRequest.getRequestId());
        }

        wbAds.setAds(matchListAds);

        return wbAds;
    }

    private AdResponse matchTagAds(AdResponse matchListAds, String correctPhoneNum, String requestId) {
        if (ApiContants.NO_AD_SHOW_CODE == matchListAds.getCode()) {
            return matchListAds;
        }

        AdTagDto tagDto = tagOrderService.getTagInfo(correctPhoneNum);

        if (CollectionUtils.isEmpty(tagDto.getData())) {
            log.info("当前号码[{}]没有返回标签信息", correctPhoneNum);
            return adService.getNoMatchAds(requestId);
        }

        List<String> tags = tagDto.getData()
                .stream()
                .map(TagVo::getName)
                .collect(Collectors.toList());

        AdOrderDto orderDto = tagOrderService.getOrderInfo(Tags.builder().tags(tags).build());

        if (CollectionUtils.isEmpty(orderDto.getData())) {
            log.info("当前号码[{}]没有返回工单信息",correctPhoneNum);
            return adService.getNoMatchAds(requestId);
        }

        List<String> orderIds = orderDto.getData()
                .stream()
                .map(adOrderVo -> String.valueOf(adOrderVo.getTicketNo()))
                .collect(Collectors.toList());

        List<Ad> matchOrderIdAds = matchListAds.getAds()
                .stream()
                .filter(a -> orderIds.contains(a.getOrderId()))
                .collect(Collectors.toList());

        if (matchOrderIdAds.size() == 0) {
            log.info("当前号码[{}]匹配的工单数为 0", correctPhoneNum);
            return adService.getNoMatchAds(requestId);
        }

        matchListAds.setAds(matchOrderIdAds);

        return matchListAds;
    }
}
