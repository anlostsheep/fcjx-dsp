package com.richstonedt.fcjx.advertisement.strategy;

import com.richstonedt.fcjx.advertisement.domain.WbSyncAd;

/**
 * <b><code>SyncAdStrategy</code></b>
 * <p/>
 * 哇棒-丰石广告同步策略
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 18:53.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public interface SyncAdStrategy {

    /**
     * 广告同步操作
     *
     * @param wbSyncAd 广告数据
     */
    void syncAd(WbSyncAd wbSyncAd);
}
