package com.richstonedt.fcjx.dsp.paas.tag.config;

import com.richstonedt.fcjx.dsp.common.redis.RedisLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <b><code>RedisConfig</code></b>
 * <p/>
 * Redis配置
 * <p/>
 * <b>Creation Time:</b> 2020/2/22 17:14.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisLock redisLock(RedisTemplate<String, String> redisTemplate) {
        return new RedisLock(redisTemplate);
    }
}