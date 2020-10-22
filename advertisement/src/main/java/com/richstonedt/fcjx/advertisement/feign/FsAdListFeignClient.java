package com.richstonedt.fcjx.advertisement.feign;

import com.richstonedt.fcjx.advertisement.domain.ComplaintList;
import com.richstonedt.fcjx.advertisement.domain.WbSyncAd;
import feign.Headers;
import com.richstonedt.fcjx.advertisement.config.FeignConfig;
import com.richstonedt.fcjx.advertisement.dto.AdListDto;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * <b><code>FsFeignClient</code></b>
 * <p/>
 * 丰石广告接口 feign client
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 14:33.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@FeignClient(name = "black-white-list",
        fallbackFactory = FsAdListFeignClient.FsAdListFeignFallback.class,
        configuration = FeignConfig.class)
public interface FsAdListFeignClient {


    /**
     * 获取广告名单集合
     *
     * @param channel  渠道
     * @param region   地市
     * @param phoneNum 手机号码
     * @return 名单集合
     */
    @RequestLine("GET /ad/{channel}/{region}/{phone}")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    AdListDto getAdList(@Param("channel") String channel,
                        @Param("region") String region,
                        @Param("phone") String phoneNum);

    /**
     * 提交投诉名单列表(全局黑名单)
     *
     * @param complaintList 投诉名单列表
     */
    @RequestLine("POST /phone-package/all-black-list")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    void commitComplaintList(ComplaintList complaintList);

    /**
     * 哇棒-丰石广告同步操作-上传
     *
     * @param wbSyncAd 哇棒广告信息
     */
    @RequestLine("POST /phone-package/ad-black-white-list")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    void uploadAd(WbSyncAd wbSyncAd);

    /**
     * 哇棒-丰石广告同步操作-广告下线
     *
     * @param advId 广告 id
     */
    @RequestLine("POST /ad/update/{advId}/0")
    @Headers({"Content-Type: application/json;charset=UTF-8", "Accept: application/json;charset=UTF-8"})
    void offAd(@Param("advId") String advId);

    @Component
    @Slf4j
    class FsAdListFeignFallback implements FallbackFactory<FsAdListFeignClient> {

        @Override
        public FsAdListFeignClient create(Throwable throwable) {
            log.error("丰石广告集合请求发生熔断,原因:", throwable);
            return new FsAdListFeignClient() {
                @Override
                public AdListDto getAdList(String channel, String region, String phoneNum) {
                    return null;
                }

                @Override
                public void commitComplaintList(ComplaintList complaintList) {
                }

                @Override
                public void uploadAd(WbSyncAd wbSyncAd) {
                }

                @Override
                public void offAd(String advId) {
                }
            };
        }
    }
}
