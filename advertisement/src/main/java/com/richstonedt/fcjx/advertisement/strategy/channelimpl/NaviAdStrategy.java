package com.richstonedt.fcjx.advertisement.strategy.channelimpl;

import com.alibaba.fastjson.JSON;
import com.richstonedt.fcjx.advertisement.contants.ApiContants;
import com.richstonedt.fcjx.advertisement.dao.AdCacheDao;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.service.AdService;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <b><code>NaviAdStrategy</code></b>
 * <p/>
 * 404 渠道广告获取策略
 * <p/>
 * <b>Creation Time:</b> 2020/4/10 17:28.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("navi")
@Slf4j
public class NaviAdStrategy implements ChannelAdStrategy {

    private final AdService adService;
    private final AdCacheDao adCacheDao;

    @Value("${cache.second.navi}")
    private String naviCacheSecond;

    public NaviAdStrategy(AdService adService,
                          AdCacheDao adCacheDao) {
        this.adService = adService;
        this.adCacheDao = adCacheDao;
    }

    @Override
    public AdResponse getChannelAd(AdRequest adRequest, String channel) {
        log.info("404 渠道广告获取策略执行...");

        String naviCacheHashField = String.valueOf(adRequest.getAd().getWidth()) + adRequest.getAd().getHeight();
        AdResponse adCache = adCacheDao.getAdCache(ApiContants.NAVI_CACHE_KEY, naviCacheHashField);

        return Optional.ofNullable(adCache)
                .orElseGet(() -> {
                    AdResponse adResponse;

                    AdResponse wbAds = adService.getWbAds(adRequest, channel);

                    if (ApiContants.NO_AD_SHOW_CODE == wbAds.getCode() || wbAds.getAds().size() == 1) {
                        adResponse = wbAds;
                    }
                    // 能够竞价的广告数量大于 1
                    else {
                        adResponse = adService.getRtbAds(adRequest, channel, wbAds);
                    }

                    adResponse.setRequestId(adRequest.getRequestId());
                    Optional.of(adResponse)
                            .filter(ad1 -> ApiContants.NO_AD_SHOW_CODE != ad1.getCode())
                            .ifPresent(ad2 -> ad2.setMessage(ApiContants.REQUEST_SUCCESS));

                    adCacheDao.setAdCache(ApiContants.NAVI_CACHE_KEY, naviCacheHashField, JSON.toJSONString(adResponse), Integer.parseInt(naviCacheSecond));

                    return adResponse;
                });

    }

}
