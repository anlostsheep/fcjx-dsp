package com.richstonedt.fcjx.advertisement.chain;

import com.richstonedt.fcjx.advertisement.contants.ApiContants;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.dto.AdListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <b><code>GlobalBlackListHandler</code></b>
 * <p/>
 * 全局黑名单广告返回
 * <p/>
 * <b>Creation Time:</b> 2020/4/30 11:42.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component("globalBlackListAdType")
@Slf4j
public class GlobalBlackListAdTypeHandler extends AbstractAdTypeHandler {

    @Override
    public AdResponse handleAdType(AdRequest adRequest, AdListDto adListDto, String channel) {
        if (ApiContants.GLOBAL_BLACK_LIST_CODE == adListDto.getCode()) {
            log.info("全局黑名单类型处理...");
            log.info("当前号码[{}]处于全局黑名单中", adRequest.getDevice().getPhoneNum());

            return setGlobalBlackListResponse(adRequest.getRequestId());
        }

        return nextHandler.handleAdType(adRequest, adListDto, channel);
    }

}
