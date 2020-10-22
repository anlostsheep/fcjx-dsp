package com.richstonedt.fcjx.advertisement.feign;

import com.richstonedt.fcjx.advertisement.config.FeignConfig;
import com.richstonedt.fcjx.advertisement.dto.AdTagDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <b><code>FsOrderInfoFeignClient</code></b>
 * <p/>
 * 号码对应标签包含的工单信息获取 feign 客户端
 * <p/>
 * <b>Creation Time:</b> 2020/4/8 16:36.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@FeignClient(name = "paas-tag",
        fallbackFactory = FsTagInfoFeignClient.FsTagInfoFallbackFactory.class,
        configuration = FeignConfig.class)
public interface FsTagInfoFeignClient {

    /**
     * 获取手机号码对应标签信息含有的工单
     *
     * @param phoneNum 手机号码
     * @return 工单信息
     */
    @RequestLine("GET /paas/tag/{phoneNum}")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    AdTagDto getTagInfo(@Param("phoneNum") String phoneNum);

    @Component
    @Slf4j
    class FsTagInfoFallbackFactory implements FallbackFactory<FsTagInfoFeignClient> {

        @Override
        public FsTagInfoFeignClient create(Throwable throwable) {
            log.error("丰石标签信息请求发生熔断,原因:", throwable);
            return phoneNum -> null;
        }
    }
}
