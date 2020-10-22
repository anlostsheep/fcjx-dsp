package com.richstonedt.fcjx.advertisement.controller;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.advertisement.api.SyncAdResponse;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.domain.ComplaintList;
import com.richstonedt.fcjx.advertisement.domain.ComplaintPhone;
import com.richstonedt.fcjx.advertisement.feign.FsAdListFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

/**
 * <b><code>ComplaintListController</code></b>
 * <p/>
 * 投诉名单控制器
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 15:32.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@RestController
@RequestMapping("/allBlacklist")
@Validated
public class ComplaintListController {

    private ThreadPoolTaskExecutor taskExecutor;

    private FsAdListFeignClient feignClient;

    @Autowired
    public void setFeignClient(FsAdListFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Autowired
    @Qualifier("dspThreadPoolExecutor")
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @PostMapping("/push")
    public DeferredResult<SyncAdResponse> commitComplaintList(@RequestBody @Valid ComplaintPhone complaintPhone) {
        DeferredResult<SyncAdResponse> result = new DeferredResult<>(5000L);

        result.onTimeout(
                () -> {
                    log.warn("投诉名单上传接口 Timeout");
                    result.setErrorResult(
                            SyncAdResponse.builder()
                                    .errorInfo("Request Timeout")
                                    .resultStatus(ApiEnum.GATEWAY_TIMEOUT.getMessage())
                                    .httpCode(ApiEnum.GATEWAY_TIMEOUT.getCode())
                                    .build()
                    );
                }
        );

        CompletableFuture<SyncAdResponse> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    feignClient.commitComplaintList(
                            ComplaintList.builder()
                                    .phoneNums(Lists.newArrayList(complaintPhone.getPhoneNumber()))
                                    .build()
                    );
                    return SyncAdResponse.builder()
                            .resultStatus(ApiEnum.SUCCESS.getMessage())
                            .httpCode(ApiEnum.SUCCESS.getCode())
                            .build();
                }, taskExecutor)
                .exceptionally(
                        (throwable -> {
                            log.error("线程池线程" + Thread.currentThread().getName() + "发生异常", throwable);
                            return SyncAdResponse.builder()
                                    .resultStatus(ApiEnum.INTERNAL_ERROR.getMessage())
                                    .httpCode(ApiEnum.INTERNAL_ERROR.getCode())
                                    .build();
                        })
                );

        completableFuture.thenAcceptAsync(result::setResult);

        return result;
    }
}
