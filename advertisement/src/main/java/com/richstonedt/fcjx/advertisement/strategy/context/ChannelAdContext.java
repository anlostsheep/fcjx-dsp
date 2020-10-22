package com.richstonedt.fcjx.advertisement.strategy.context;

import com.google.common.collect.Maps;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <b><code>ChannelAdContext</code></b>
 * <p/>
 * 渠道广告策略上下文
 * <p/>
 * <b>Creation Time:</b> 2020/4/10 17:34.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component
@Slf4j
public class ChannelAdContext {

    private final Map<String, ChannelAdStrategy> strategyMap = Maps.newConcurrentMap();

    @Autowired
    private void injectStrategy(Map<String, ChannelAdStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach(this.strategyMap::put);
        log.info("渠道广告策略列表:{}", strategyMap);
    }

    public ChannelAdStrategy getContextStrategy(String strategyName) {
        return this.strategyMap.get(strategyName);
    }
}
