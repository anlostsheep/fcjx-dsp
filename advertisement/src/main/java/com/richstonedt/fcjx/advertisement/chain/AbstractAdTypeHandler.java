package com.richstonedt.fcjx.advertisement.chain;

import com.richstonedt.fcjx.advertisement.contants.ApiContants;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.dto.AdListDto;

import java.util.Optional;

/**
 * <b><code>AbstractHandler</code></b>
 * <p/>
 * 抽象处理
 * <p/>
 * <b>Creation Time:</b> 2020/4/30 11:40.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public abstract class AbstractAdTypeHandler {

    public AbstractAdTypeHandler nextHandler;

    public void setNextHandler(AbstractAdTypeHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * 处理号码类型方法
     *
     * @param adRequest 请求参数
     * @param adListDto 广告内容
     * @param channel   渠道
     * @return 响应
     */
    public abstract AdResponse handleAdType(AdRequest adRequest, AdListDto adListDto, String channel);

    public AdResponse setGlobalBlackListResponse(String requestId) {
        return AdResponse.builder()
                .code(ApiContants.NO_AD_SHOW_CODE)
                .message("The current number belongs to the complaint list!")
                .requestId(requestId)
                .build();
    }

    public String getCorrectPhoneNum(String originalPhoneNum) {
        return Optional.ofNullable(originalPhoneNum)
                .map(p -> p.startsWith("86") ? p : "86" + p)
                .map(c -> c.substring(2))
                .get();
    }
}
