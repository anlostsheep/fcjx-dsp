package com.richstonedt.fcjx.advertisement.strategy.channelimpl;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <b><code>ToolbarAdStrategy</code></b>
 * <p/>
 * toolbar 渠道广告获取策略
 * <p/>
 * <b>Creation Time:</b> 2020/4/10 17:26.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("toolbar")
@Slf4j
public class ToolbarAdStrategy implements ChannelAdStrategy {

    @Override
    public AdResponse getChannelAd(AdRequest adRequest, String channel) {
        log.info("toolbar 渠道广告获取策略");
        return AdResponse.builder()
                .message("toolbar 渠道广告")
                .code(ApiEnum.NO_MATCH_AD_CODE.getCode())
                .requestId(adRequest.getRequestId())
                .ads(Lists.newArrayList())
                .build();
    }
}
