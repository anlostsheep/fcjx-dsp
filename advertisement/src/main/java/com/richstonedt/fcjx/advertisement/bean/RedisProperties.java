package com.richstonedt.fcjx.advertisement.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * <b><code>RedisProperties</code></b>
 * <p/>
 * redis 服务器配置类
 * <p/>
 * <b>Creation Time:</b> 2020/5/18 13:05.
 *
 * @author dengzhen
 * @since fcjx-dsp 0.1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@EnableConfigurationProperties(RedisProperties.class)
@Data
@RefreshScope
public class RedisProperties {
    
    private int database;
    
    private String host;
    
    private int port;
    
    private String timeout;
    
    private String password;
}
