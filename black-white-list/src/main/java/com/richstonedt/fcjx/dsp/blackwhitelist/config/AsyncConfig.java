package com.richstonedt.fcjx.dsp.blackwhitelist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * <b><code>AsyncConfig</code></b>
 * <p/>
 * SpringMVC异步请求处理配置
 * <p/>
 * <b>Creation Time:</b> 2020/2/22 17:14.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(500);
        executor.initialize();
        return executor;
    }
}