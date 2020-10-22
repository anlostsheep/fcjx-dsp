package com.richstonedt.fcjx.advertisement.controller;

import com.richstonedt.fcjx.advertisement.api.SyncAdResponse;
import com.richstonedt.fcjx.advertisement.bean.ChannelMap;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.domain.WbSyncAd;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import com.richstonedt.fcjx.advertisement.strategy.SyncAdStrategy;
import com.richstonedt.fcjx.advertisement.strategy.context.SyncAdContext;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * <b><code>SyncAdController</code></b>
 * <p/>
 * 哇棒-丰石广告同步控制器
 * <p/>
 * <b>Creation Time:</b> 2020/5/9 15:30.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@RestController
@Validated
@RequestMapping("/downloadPackage")
public class SyncAdController {

    private SyncAdContext syncAdContext;
    private ThreadPoolTaskExecutor taskExecutor;
    private ChannelMap channelMap;

    @Autowired
    public void setChannelMap(ChannelMap channelMap) {
        this.channelMap = channelMap;
    }

    @Autowired
    public void setSyncAdContext(SyncAdContext syncAdContext) {
        this.syncAdContext = syncAdContext;
    }

    @Autowired
    @Qualifier("dspThreadPoolExecutor")
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @PostMapping("/apiNotice")
    public DeferredResult<SyncAdResponse> syncAd(@RequestBody @Valid WbSyncAd wbSyncAd) {
        DeferredResult<SyncAdResponse> result = new DeferredResult<>(5000L);

        result.onTimeout(
                () -> {
                    log.warn("哇棒-丰石广告同步操作接口 Timeout");
                    result.setErrorResult(
                            SyncAdResponse.builder()
                                    .httpCode(ApiEnum.GATEWAY_TIMEOUT.getCode())
                                    .resultStatus(ApiEnum.GATEWAY_TIMEOUT.getMessage())
                                    .errorInfo("Request Timeout!")
                                    .build()
                    );
                }
        );

        CompletableFuture<SyncAdResponse> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    String option = channelMap.option.get(wbSyncAd.getOpType());
                    String optOption = Optional.ofNullable(option)
                            .orElseThrow(() -> new SmartPushException(ApiEnum.BAD_REQUEST));
                    
                    SyncAdStrategy syncAdStrategy = syncAdContext.getSyncAdStrategy(optOption);
                    syncAdStrategy.syncAd(wbSyncAd);

                    return SyncAdResponse.builder()
                            .httpCode(ApiEnum.SUCCESS.getCode())
                            .resultStatus(ApiEnum.SUCCESS.getMessage())
                            .build();
                }, taskExecutor)
                .exceptionally(
                        e -> {
                            log.error("线程池线程" + Thread.currentThread().getName() + "执行异常", e);
                            return SyncAdResponse.builder()
                                    .httpCode(ApiEnum.INTERNAL_ERROR.getCode())
                                    .resultStatus(ApiEnum.INTERNAL_ERROR.getMessage())
                                    .errorInfo(e.getMessage())
                                    .build();
                        }
                );

        completableFuture.thenAccept(result::setResult);

        return result;
    }
}
