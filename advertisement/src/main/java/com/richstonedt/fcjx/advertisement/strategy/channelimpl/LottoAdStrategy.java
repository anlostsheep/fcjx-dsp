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
 * <b><code>LottoAdStrategy</code></b>
 * <p/>
 * 大转盘广告获取策略
 * <p/>
 * <b>Creation Time:</b> 2020/4/14 0:15.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("lotto")
@Slf4j
public class LottoAdStrategy implements ChannelAdStrategy {

    @Value("${cache.second.lotto}")
    private String lottoCacheTime;

    private final AdService adService;
    private final AdCacheDao adCacheDao;

    public LottoAdStrategy(AdService adService,
                           AdCacheDao adCacheDao) {
        this.adService = adService;
        this.adCacheDao = adCacheDao;
    }

    @Override
    public AdResponse getChannelAd(AdRequest adRequest, String channel) {
        log.info("大转盘渠道广告获取策略执行...");

        String lottoCacheHashField = String.valueOf(adRequest.getAd().getWidth()) + adRequest.getAd().getHeight();

        AdResponse adCache = adCacheDao.getAdCache(ApiContants.LOTTO_CACHE_KEY, lottoCacheHashField);

        return Optional.ofNullable(adCache)
                .orElseGet(() -> {
                    AdResponse adResponse;

                    AdResponse wbAds = adService.getWbAds(adRequest, channel);

                    if (ApiContants.NO_AD_SHOW_CODE == wbAds.getCode() || wbAds.getAds().size() == 1) {
                        adResponse = wbAds;
                    }
                    //
                    else {
                        adResponse = adService.getRtbAds(adRequest, channel, wbAds);
                    }

                    adCacheDao.setAdCache(ApiContants.LOTTO_CACHE_KEY, lottoCacheHashField, JSON.toJSONString(adResponse), Integer.parseInt(lottoCacheTime));
                    return adResponse;
                });


    }
}
