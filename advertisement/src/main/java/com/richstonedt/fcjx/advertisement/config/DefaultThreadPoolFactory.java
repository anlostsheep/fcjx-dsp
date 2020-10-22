package com.richstonedt.fcjx.advertisement.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <b><code>DefaultThreadPoolFactory</code></b>
 * <p/>
 * 线程池工厂构建
 * <p/>
 * <b>Creation Time:</b> 2020/5/8 17:44.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Configuration
@Slf4j
public class DefaultThreadPoolFactory {

    @Bean(name = "dspThreadPoolExecutor")
    public ThreadPoolTaskExecutor executorBuilder() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat("dsp-thread-pool-%d")
                .setUncaughtExceptionHandler((t, e) -> {
                    log.info("线程池内线程[{}]发生异常", t);
                    t.interrupt();
                    log.error("线程异常:", e);
                })
                .build();

        // 设置线程工厂
        executor.setThreadFactory(threadFactory);
        // 核心线程数大小（CPU 个数乘以 2 + 1）
        executor.setCorePoolSize(2 * Runtime.getRuntime().availableProcessors() + 1);
        // 最大线程数大小（CPU 个数乘以 10）
        executor.setMaxPoolSize(10 * Runtime.getRuntime().availableProcessors());
        // 设置线程池中的队列大小
        executor.setQueueCapacity(2000);
        // 设置线程池中线程存活时间/s
        executor.setKeepAliveSeconds(6);
        // 设置线程池队列满时的拒绝策略: CallerRunsPolicy() 不进入线程池中的线程处理,由调用此方法的线程处理
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();

        return executor;
    }
}
