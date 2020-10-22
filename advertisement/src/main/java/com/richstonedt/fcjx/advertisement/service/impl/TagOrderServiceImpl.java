package com.richstonedt.fcjx.advertisement.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.dto.AdOrderDto;
import com.richstonedt.fcjx.advertisement.dto.AdTagDto;
import com.richstonedt.fcjx.advertisement.dto.Tags;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import com.richstonedt.fcjx.advertisement.feign.FsOrderInfoFeignClient;
import com.richstonedt.fcjx.advertisement.feign.FsTagInfoFeignClient;
import com.richstonedt.fcjx.advertisement.service.TagOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <b><code>TagOrderServiceImpl</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/12 20:25.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Service
@Slf4j
public class TagOrderServiceImpl implements TagOrderService {

    private final FsTagInfoFeignClient tagInfoFeignClient;
    private final FsOrderInfoFeignClient orderInfoFeignClient;

    public TagOrderServiceImpl(FsTagInfoFeignClient tagInfoFeignClient,
                               FsOrderInfoFeignClient orderInfoFeignClient) {
        this.tagInfoFeignClient = tagInfoFeignClient;
        this.orderInfoFeignClient = orderInfoFeignClient;
    }

    @Override
    public AdTagDto getTagInfo(String phoneNum) {
        AdTagDto tagOrderDto = tagInfoFeignClient.getTagInfo(phoneNum);

        AdTagDto optTagDto = Optional.ofNullable(tagOrderDto)
                .orElseThrow(() -> new SmartPushException(ApiEnum.GATEWAY_TIMEOUT));
        log.info("当前手机号匹配[{}]对应标签包含的标签有:{}", phoneNum, JSONObject.toJSONString(optTagDto));

        return optTagDto;
    }

    @Override
    public AdOrderDto getOrderInfo(Tags tags) {
        AdOrderDto orderInfo = orderInfoFeignClient.getOrderInfo(tags);

        AdOrderDto optOrderDto = Optional.ofNullable(orderInfo)
                .orElseThrow(() -> new SmartPushException(ApiEnum.GATEWAY_TIMEOUT));
        log.info("当前请求匹配工单信息:{}", JSON.toJSONString(optOrderDto));

        return optOrderDto;
    }
}
