package com.richstonedt.fcjx.advertisement.service;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * <b><code>BaseAdService</code></b>
 * <p/>
 * 抽象广告处理基类
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 13:18.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
public abstract class BaseAdService {

    /**
     * 获取号码在投诉列表中的响应
     *
     * @param adRequest 广告请求
     * @return 响应信息
     */
    protected AdResponse getGlobalBlackListAd(AdRequest adRequest) {
        return AdResponse.builder()
                .code(ApiEnum.GLOBAL_BLACK_LIST_AD.getCode())
                .message(ApiEnum.GLOBAL_BLACK_LIST_AD.getMessage())
                .requestId(adRequest.getRequestId())
                .ads(Lists.newArrayList())
                .build();
    }

    /**
     * 返回没有匹配广告信息
     *
     * @param adRequest 广告请求
     * @return no ad
     */
    protected AdResponse getNoMatchAdResponse(AdRequest adRequest) {
        return AdResponse.builder()
                .code(ApiEnum.NO_MATCH_AD_CODE.getCode())
                .message(ApiEnum.NO_MATCH_AD_CODE.getMessage())
                .requestId(adRequest.getRequestId())
                .ads(Lists.newArrayList())
                .build();
    }

    /**
     * 添加响应信息
     *
     * @param adResponse 广告响应
     * @param requestId  请求 id
     * @return adResponse
     */
    protected AdResponse addResponseMsg(AdResponse adResponse, String requestId) {
        adResponse.setRequestId(requestId);
        Optional.of(adResponse)
                .filter(a -> !ApiEnum.NO_MATCH_AD_CODE.getCode().equals(a.getCode()))
                .ifPresent(t -> adResponse.setMessage(ApiEnum.SUCCESS.getMessage()));

        return adResponse;
    }
}
