package com.richstonedt.fcjx.advertisement.controller;

import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.domain.AdRequest;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import com.richstonedt.fcjx.advertisement.strategy.ChannelAdStrategy;
import com.richstonedt.fcjx.advertisement.strategy.context.ChannelAdContext;
import com.richstonedt.fcjx.advertisement.validator.ChannelValid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
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
 * <b><code>AdController</code></b>
 * <p/>
 * 广告获取接口
 * <p/>
 * <b>Creation Time:</b> 2020/4/1 21:16.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Api(tags = "广告请求")
@RestController
@RequestMapping("/channelrtb")
@Slf4j
@Validated
public class AdController {

    private final ChannelAdContext channelAdContext;
    private final ThreadPoolTaskExecutor taskExecutor;

    public AdController(ChannelAdContext channelAdContext,
                        @Qualifier("dspThreadPoolExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.channelAdContext = channelAdContext;
        this.taskExecutor = taskExecutor;
    }

    @ApiOperation(value = "广告获取", notes = "获取对应广告位的实时竞价广告")
    @ApiResponses({
            @ApiResponse(code = 400, message = "未知的请求"),
            @ApiResponse(code = 401, message = "请求参数不正确"),
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping("/biding")
    public DeferredResult<AdResponse> channelAd(@RequestBody @Valid AdRequest adRequest,
                                                @ChannelValid(message = "广告渠道不存在") String channel) {

        DeferredResult<AdResponse> result = new DeferredResult<>(5000L);

        result.onTimeout(
                () -> {
                    log.warn("渠道广告接口响应处理 Timeout");
                    result.setErrorResult(
                            AdResponse.builder()
                                    .code(ApiEnum.GATEWAY_TIMEOUT.getCode())
                                    .message(ApiEnum.GATEWAY_TIMEOUT.getMessage())
                                    .requestId(adRequest.getRequestId())
                                    .build()
                    );
                }
        );

        CompletableFuture<AdResponse> completableFuture = CompletableFuture.supplyAsync(
                () -> {
                    ChannelAdStrategy channelAdStrategy = channelAdContext.getContextStrategy(channel);
                    ChannelAdStrategy optStrategy = Optional.ofNullable(channelAdStrategy)
                            .orElseThrow(() -> new SmartPushException(ApiEnum.BAD_REQUEST));

                    return optStrategy.getChannelAd(adRequest, channel);
                }, taskExecutor)
                .exceptionally(
                        throwable -> {
                            log.error("渠道广告获取线程池线程" + Thread.currentThread().getName() + "执行异常:", throwable);
                            return AdResponse.builder()
                                    .requestId(adRequest.getRequestId())
                                    .code(ApiEnum.INTERNAL_ERROR.getCode())
                                    .message(ApiEnum.INTERNAL_ERROR.getMessage())
                                    .build();
                        }
                );

        completableFuture.thenAcceptAsync(result::setResult);

        return result;
    }
}
