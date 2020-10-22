package com.richstonedt.fcjx.advertisement.strategy;

import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;

/**
 * <b><code>ChannelAdStrategy</code></b>
 * <p/>
 * 广告策略接口
 * <p/>
 * <b>Creation Time:</b> 2020/4/10 17:24.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public interface ChannelAdStrategy {

    /**
     * 广告策略接口-抽象渠道广告方法
     *
     * @param adRequest 广告请求
     * @param channel   渠道
     * @return 广告响应
     */
    AdResponse getChannelAd(AdRequest adRequest, String channel);
}
