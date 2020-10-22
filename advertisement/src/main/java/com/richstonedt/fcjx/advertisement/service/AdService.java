package com.richstonedt.fcjx.advertisement.service;

import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.dto.AdListDto;

/**
 * <b><code>AdService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 11:12.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public interface AdService extends BaseService<AdRequest, AdResponse> {

    /**
     * 获取哇棒广告
     *
     * @param adRequest 广告请求参数
     * @param channel   渠道
     * @return 哇棒广告
     */
    AdResponse getWbAds(AdRequest adRequest, String channel);

    /**
     * 获取丰石广告(由哇棒同步)
     *
     * @param adRequest 广告请求参数
     * @param channel   渠道
     * @return 丰石广告(哇棒同步)
     */
    AdListDto getFsAds(AdRequest adRequest, String channel);

    /**
     * 返回没有匹配广告信息
     *
     * @param requestId 广告请求 id
     * @return 没有匹配的信息
     */
    AdResponse getNoMatchAds(String requestId);

    /**
     * 获取 rtb 实时竞价广告
     *
     * @param adRequest  广告请求
     * @param channel    渠道
     * @param preciseAds 需要竞价的广告 id 集合
     * @return 实时竞价广告
     */
    AdResponse getRtbAds(AdRequest adRequest, String channel, AdResponse preciseAds);
}
