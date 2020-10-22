package com.richstonedt.fcjx.advertisement.strategy.context;

import com.google.common.collect.Maps;
import com.richstonedt.fcjx.advertisement.strategy.SyncAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <b><code>SyncAdContext</code></b>
 * <p/>
 * 广告同步上下文
 * <p/>
 * <b>Creation Time:</b> 2020/5/11 10:22.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@Component
public class SyncAdContext {

    private final Map<String, SyncAdStrategy> syncAdStrategyMap = Maps.newConcurrentMap();

    @Autowired
    private void injectStrategy(Map<String, SyncAdStrategy> syncAdStrategyMap) {
        this.syncAdStrategyMap.clear();
        syncAdStrategyMap.forEach(this.syncAdStrategyMap::put);

        log.info("广告同步策略列表:{}", this.syncAdStrategyMap);
    }

    public SyncAdStrategy getSyncAdStrategy(String strategyName) {
        return this.syncAdStrategyMap.get(strategyName);
    }
}
