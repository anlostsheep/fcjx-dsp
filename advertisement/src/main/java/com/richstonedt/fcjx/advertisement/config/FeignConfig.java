package com.richstonedt.fcjx.advertisement.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import feign.Contract;
import feign.Logger;
import feign.hystrix.SetterFactory;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>FeignConfig</code></b>
 * <p/>
 * Feign 客户端配置类
 * <p/>
 * <b>Creation Time:</b> 2020/4/7 14:35.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */

@Configuration
public class FeignConfig {

    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public Logger feignLogger() {
        return new Logger.ErrorLogger();
    }

    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public OkHttpClient feignClient() {
        return new OkHttpClient();
    }

    @Bean
    public SetterFactory feignSetterFactory() {
        return (target, method) -> HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("dsp-hystrix-key"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.defaultSetter()
                                .withExecutionTimeoutEnabled(true)
                                .withFallbackEnabled(true)
                                .withExecutionIsolationThreadInterruptOnTimeout(true)
                                .withExecutionTimeoutInMilliseconds(2000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.defaultSetter()
                                .withCoreSize(2 * Runtime.getRuntime().availableProcessors() + 1)
                );
    }
}
