package com.richstonedt.fcjx.dsp.paas.tag.service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.richstonedt.fcjx.dsp.paas.tag.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.paas.tag.exception.ApiException;
import com.richstonedt.fcjx.dsp.paas.tag.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <b><code>BaseService</code></b>
 * <p/>
 * 服务层基类，完成一些通用操作
 * <p/>
 * <b>Creation Time:</b> 2020/2/19 1:40.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Service
@Slf4j
public class BaseService {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setDaemon(true)
            .setUncaughtExceptionHandler((t, e) -> log.error("线程池发生了异常：{}， {}", t, e))
            .setNameFormat("BaseService thread")
            .build();
    private final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(5, 35, 5000L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(2000), threadFactory);

    /**
     * 异步处理请求
     * @param timeout
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> DeferredResult<T> processRequestAsync(Long timeout, Supplier<T> supplier) {
        ApiException timeoutException = new ApiException(MyResponseCode.TIME_OUT.getCode(), MyResponseCode.TIME_OUT.getMsg());
        DeferredResult<T> deferredResult = new DeferredResult<>(timeout);
        deferredResult.onTimeout(() -> deferredResult.setErrorResult(timeoutException));
        executor.submit( () -> {
            try {
                deferredResult.setResult(supplier.get());
            } catch (Exception e) {
                globalExceptionHandler.handle(e);
                log.error("异步处理请求发生了线程池内调用异常: {}", e.getMessage());
            }
        } );
        return deferredResult;
    }
}
