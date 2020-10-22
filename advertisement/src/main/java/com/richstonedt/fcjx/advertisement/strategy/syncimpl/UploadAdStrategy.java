package com.richstonedt.fcjx.advertisement.strategy.syncimpl;

import com.alibaba.fastjson.JSON;
import com.richstonedt.fcjx.advertisement.bean.ChannelMap;
import com.richstonedt.fcjx.advertisement.domain.WbSyncAd;
import com.richstonedt.fcjx.advertisement.feign.FsAdListFeignClient;
import com.richstonedt.fcjx.advertisement.strategy.SyncAdStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <b><code>UploadAdStrategy</code></b>
 * <p/>
 * 广告上线
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 19:12.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@Component("online")
public class UploadAdStrategy implements SyncAdStrategy {

    private FsAdListFeignClient feignClient;
    private ChannelMap channelMap;

    @Autowired
    public void setChannelMap(ChannelMap channelMap) {
        this.channelMap = channelMap;
    }

    @Autowired
    public void setFeignClient(FsAdListFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public void syncAd(WbSyncAd wbSyncAd) {
        log.info("哇棒-丰石广告同步操作-上线,广告内容:{}", JSON.toJSONString(wbSyncAd));
        
        Optional.ofNullable(wbSyncAd)
                .filter(ad1 -> ad1.getRegion().length == 0)
                .ifPresent(ad2 -> ad2.setRegion(channelMap.regions));

        feignClient.uploadAd(wbSyncAd);
    }
}
