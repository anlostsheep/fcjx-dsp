package com.richstonedt.fcjx.advertisement.feign;

import com.richstonedt.fcjx.advertisement.config.FeignConfig;
import com.richstonedt.fcjx.advertisement.dto.AdOrderDto;
import com.richstonedt.fcjx.advertisement.dto.Tags;
import feign.Headers;
import feign.RequestLine;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <b><code>FsOrderInfoFeignClient</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/5/19 16:10.
 *
 * @author dengzhen
 * @since fcjx-dsp 0.1.0
 */
@FeignClient(name = "dsp-ticket",
        configuration = FeignConfig.class,
        fallbackFactory = FsOrderInfoFeignClient.FsOrderInfoFallbackFactory.class)
public interface FsOrderInfoFeignClient {

    /**
     * 获取标签对应工单信息
     *
     * @param tags 标签
     * @return 工单
     */
    @RequestLine("POST /ticket/paas-tag")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    AdOrderDto getOrderInfo(Tags tags);

    @Component
    @Slf4j
    class FsOrderInfoFallbackFactory implements FallbackFactory<FsOrderInfoFeignClient> {

        @Override
        public FsOrderInfoFeignClient create(Throwable throwable) {
            log.error("丰石工单信息请求发生熔断,原因:", throwable);
            return tags -> null;
        }
    }
}
