package com.richstonedt.fcjx.advertisement.feign;

import com.richstonedt.fcjx.advertisement.config.FeignConfig;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <b><code>WbAdFeignClient</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/8 14:16.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@FeignClient(value = "wb-ad-client",
        url = "${wb.ad.url}",
        fallbackFactory = WbAdFeignClient.WbAdFeignFallbackFactory.class,
        configuration = FeignConfig.class)
public interface WbAdFeignClient {

    /**
     * 获取哇棒所有在线广告
     *
     * @param channelName 渠道名称
     * @param adRequest   广告请求
     * @return ad list
     */
    @RequestLine("POST /{channelName}/ad/get")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    AdResponse getOnlineAds(@Param("channelName") String channelName, AdRequest adRequest);

    @Component
    @Slf4j
    class WbAdFeignFallbackFactory implements FallbackFactory<WbAdFeignClient> {
        @Override
        public WbAdFeignClient create(Throwable throwable) {
            log.error("哇棒广告请求发生熔断,原因:", throwable);
            return (channelName, adRequest) -> null;
        }
    }
}
