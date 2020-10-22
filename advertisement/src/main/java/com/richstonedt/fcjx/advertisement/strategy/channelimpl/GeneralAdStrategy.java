package com.richstonedt.fcjx.advertisement.strategy.channelimpl;

import com.richstonedt.fcjx.advertisement.chain.AbstractAdTypeHandler;
import com.richstonedt.fcjx.advertisement.contants.ApiContants;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.dto.AdListDto;
import com.richstonedt.fcjx.advertisement.service.AdService;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <b><code>GeneralAdStrategy</code></b>
 * <p/>
 * 通用精准投放广告获取策略
 * <p/>
 * <b>Creation Time:</b> 2020/4/14 0:27.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component
@Slf4j
public class GeneralAdStrategy implements ChannelAdStrategy {

    private final AdService adService;

    private final AbstractAdTypeHandler globalBlackListAdTypeHandler;
    private final AbstractAdTypeHandler noRecordAdTypeHandler;
    private final AbstractAdTypeHandler singleAdTypeHandler;

    public GeneralAdStrategy(AdService adService,
                             @Qualifier("globalBlackListAdType") AbstractAdTypeHandler globalBlackListAdTypeHandler,
                             @Qualifier("noRecordAdType") AbstractAdTypeHandler noRecordAdTypeHandler,
                             @Qualifier("singleAdType") AbstractAdTypeHandler singleAdTypeHandler) {
        this.adService = adService;
        this.globalBlackListAdTypeHandler = globalBlackListAdTypeHandler;
        this.noRecordAdTypeHandler = noRecordAdTypeHandler;
        this.singleAdTypeHandler = singleAdTypeHandler;
    }

    @Override
    public AdResponse getChannelAd(AdRequest adRequest, String channel) {
        log.info("通用广告获取策略执行...");

        AdListDto fsAdsDto = adService.getFsAds(adRequest, channel);

        globalBlackListAdTypeHandler.setNextHandler(noRecordAdTypeHandler);
        noRecordAdTypeHandler.setNextHandler(singleAdTypeHandler);

        AdResponse matchAds = globalBlackListAdTypeHandler.handleAdType(adRequest, fsAdsDto, channel);

        AdResponse adResponse;
        if (ApiContants.NO_AD_SHOW_CODE == matchAds.getCode() || matchAds.getAds().size() == 1) {
            adResponse = matchAds;
        }
        // 能够竞价的广告数量大于 1
        else {
            adResponse = adService.getRtbAds(adRequest, channel, matchAds);
        }

        adResponse.setRequestId(adRequest.getRequestId());
        Optional.of(adResponse)
                .filter(ad1 -> ApiContants.NO_AD_SHOW_CODE != ad1.getCode())
                .ifPresent(ad2 -> ad2.setMessage(ApiContants.REQUEST_SUCCESS));

        return adResponse;
    }

}
