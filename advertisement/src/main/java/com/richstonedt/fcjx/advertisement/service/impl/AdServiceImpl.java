package com.richstonedt.fcjx.advertisement.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.richstonedt.fcjx.advertisement.bean.ChannelMap;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.dao.MobileLocalMapper;
import com.richstonedt.fcjx.advertisement.domain.Ad;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.domain.Advertisement;
import com.richstonedt.fcjx.advertisement.dto.AdListDto;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import com.richstonedt.fcjx.advertisement.feign.FsAdListFeignClient;
import com.richstonedt.fcjx.advertisement.feign.WbAdFeignClient;
import com.richstonedt.fcjx.advertisement.service.AdService;
import com.richstonedt.fcjx.advertisement.service.BaseAdService;
import com.richstonedt.fcjx.advertisement.service.MoblieLocalRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <b><code>AdServiceImpl</code></b>
 * <p/>
 * 广告业务处理
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 13:16.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Service
@Slf4j
public class AdServiceImpl extends BaseAdService implements AdService {

    private final ChannelMap channelMap;

    private final WbAdFeignClient wbAdFeignClient;

    private final MobileLocalMapper mobileLocalMapper;

    private final FsAdListFeignClient fsAdListFeignClient;

    public AdServiceImpl(ChannelMap channelMap,
                         WbAdFeignClient wbAdFeignClient,
                         MobileLocalMapper mobileLocalMapper,
                         FsAdListFeignClient fsAdListFeignClient) {
        this.channelMap = channelMap;
        this.wbAdFeignClient = wbAdFeignClient;
        this.mobileLocalMapper = mobileLocalMapper;
        this.fsAdListFeignClient = fsAdListFeignClient;
    }

    @Override
    public AdResponse getWbAds(AdRequest adRequest, String channel) {
        String channelName = channelMap.names.get(channel);

        String channelId = channelMap.ids.get(channel);
        adRequest.setChannelId(channelId);

        AdResponse onlineAds = wbAdFeignClient.getOnlineAds(channelName, adRequest);

        AdResponse notNullAdResp = Optional.ofNullable(onlineAds)
                .orElseThrow(() -> new SmartPushException(ApiEnum.GATEWAY_TIMEOUT));
        log.info("哇棒广告返回内容:{}", JSONObject.toJSONString(notNullAdResp));

        return notNullAdResp;
    }

    @Override
    public AdListDto getFsAds(AdRequest adRequest, String channel) {
        String phoneNum = adRequest.getDevice().getPhoneNum();

        String region = locationPhoneNum(phoneNum);

        AdListDto adListDto = fsAdListFeignClient.getAdList(channel, region, phoneNum);

        AdListDto optAdListDto = Optional.ofNullable(adListDto)
                .orElseThrow(() -> new SmartPushException(ApiEnum.GATEWAY_TIMEOUT));
        log.info("丰石广告接口返回内容:{}", JSONObject.toJSONString(optAdListDto));

        return optAdListDto;
    }

    @Override
    public AdResponse getNoMatchAds(String requestId) {
        return AdResponse.builder()
                .requestId(requestId)
                .message(ApiEnum.NO_MATCH_AD_CODE.getMessage())
                .code(ApiEnum.NO_MATCH_AD_CODE.getCode())
                .build();
    }

    @Override
    public AdResponse getRtbAds(AdRequest adRequest, String channel, AdResponse preciseAds) {
        List<Ad> ads = preciseAds.getAds();
        List<String> rtbAdIds = ads.stream()
                .map(Ad::getAdId)
                .collect(Collectors.toList());
        adRequest.setAdIds(rtbAdIds);

        String adSizes = String.valueOf(adRequest.getAd().getWidth()) + adRequest.getAd().getHeight();
        Optional.of(adSizes)
                .map(s -> !channelMap.counts.containsKey(s))
                .ifPresent(t -> {
                    throw new SmartPushException(ApiEnum.NO_AD_SIZE);
                });

        String channelRtbAdCount = channelMap.counts.get(adSizes);

        Advertisement ad = adRequest.getAd();
        ad.setAdCount(Integer.parseInt(channelRtbAdCount));
        adRequest.setAd(ad);

        AdResponse rtbAds = getWbAds(adRequest, channel);
        log.info("当前渠道[{}]的 rtb 广告为:{}", channel, JSONObject.toJSONString(rtbAds));

        return rtbAds;
    }

    private String locationPhoneNum(String phoneNum) {
        String correctPhoneNum = getCorrectPhoneNum(phoneNum);

        String phoneSegment = getPhoneSegment(correctPhoneNum);

        String callerloc = MoblieLocalRunner.MOBILE_LOCAL_DATA_MAP.get(phoneSegment);

        String optCallerloc = Optional.ofNullable(callerloc).orElse("GZ");
        log.info("当前请求号码[{}]归属地信息:{}", correctPhoneNum, optCallerloc);

        return optCallerloc;
    }

    public String getCorrectPhoneNum(String phoneNum) {
        return Optional.ofNullable(phoneNum)
                .map(p -> p.startsWith("86") ? p : "86" + p)
                .map(n -> n.substring(2))
                .get();
    }

    private String getPhoneSegment(String correctPhoneNum) {
        return Optional.ofNullable(correctPhoneNum)
                .map(c -> c.substring(0, 7))
                .get();
    }

}
