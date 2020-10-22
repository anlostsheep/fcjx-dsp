package com.richstonedt.fcjx.advertisement.strategy.channelimpl;

import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <b><code>TestAppStrategy</code></b>
 * <p/>
 * testapp 渠道广告获取方式
 * <p/>
 * <b>Creation Time:</b> 2020/5/19 17:39.
 *
 * @author dengzhen
 * @since fcjx-dsp 0.1.0
 */
@Component("testapp")
@Slf4j
public class TestAppStrategy implements ChannelAdStrategy {

    private GeneralAdStrategy generalAdStrategy;

    @Autowired
    public void setGeneralAdStrategy(GeneralAdStrategy generalAdStrategy) {
        this.generalAdStrategy = generalAdStrategy;
    }

    @Override
    public AdResponse getChannelAd(AdRequest adRequest, String channel) {
        log.info("testapp 渠道获取策略执行...");

        return generalAdStrategy.getChannelAd(adRequest, channel);
    }
}
