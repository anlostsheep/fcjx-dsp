package com.richstonedt.fcjx.advertisement.config;

import com.richstonedt.fcjx.advertisement.bean.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b><code>RedissonConfig</code></b>
 * <p/>
 * 单机模式的 redisson
 * <p/>
 * <b>Creation Time:</b> 2020/5/1 0:59.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Configuration
@RefreshScope
public class RedissonConfig {

    private RedisProperties redisProperties;

    @Autowired
    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Value("${redisson.address}")
    private String address;

    @Bean
    public RedissonClient getRedisson() {
        Config config = new Config();
        config.useSingleServer().setAddress(address).setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }
}
