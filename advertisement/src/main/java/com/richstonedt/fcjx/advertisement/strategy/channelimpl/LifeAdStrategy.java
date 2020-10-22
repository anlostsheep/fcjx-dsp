package com.richstonedt.fcjx.advertisement.strategy.channelimpl;

import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <b><code>LifeAdStrategy</code></b>
 * <p/>
 * 生活渠道广告获取策略
 * <p/>
 * <b>Creation Time:</b> 2020/4/10 17:30.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("gdapp")
@Slf4j
public class LifeAdStrategy implements ChannelAdStrategy {

    private final GeneralAdStrategy generalAdStrategy;

    public LifeAdStrategy(GeneralAdStrategy generalAdStrategy) {
        this.generalAdStrategy = generalAdStrategy;
    }

    @Override
    public AdResponse getChannelAd(AdRequest adRequest, String channel) {
        log.info("gdapp life 渠道广告获取策略执行...");

        return generalAdStrategy.getChannelAd(adRequest, channel);
    }
}
