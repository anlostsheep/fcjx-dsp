package com.richstonedt.fcjx.advertisement.strategy.syncimpl;

import com.alibaba.fastjson.JSON;
import com.richstonedt.fcjx.advertisement.domain.WbSyncAd;
import com.richstonedt.fcjx.advertisement.feign.FsAdListFeignClient;
import com.richstonedt.fcjx.advertisement.strategy.SyncAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <b><code>UpdateAdStrategy</code></b>
 * <p/>
 * 广告下线
 * <p/>
 * <b>Creation Time:</b> 2020/5/11 10:29.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@Component("offline")
public class OffAdStrategy implements SyncAdStrategy {

    private FsAdListFeignClient feignClient;

    @Autowired
    public void setFeignClient(FsAdListFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public void syncAd(WbSyncAd wbSyncAd) {
        log.info("哇棒-丰石广告同步操作-下线,广告内容:{}", JSON.toJSONString(wbSyncAd));

        feignClient.offAd(wbSyncAd.getAdvId());
    }
}
