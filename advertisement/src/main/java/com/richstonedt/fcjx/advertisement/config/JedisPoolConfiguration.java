package com.richstonedt.fcjx.advertisement.config;

import com.richstonedt.fcjx.advertisement.bean.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <b><code>JedisConfig</code></b>
 * <p/>
 * JedisPool 配置类
 * <p/>
 * <b>Creation Time:</b> 2020/5/1 21:41.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Configuration
@EnableCaching
public class JedisPoolConfiguration extends CachingConfigurerSupport {

    private RedisProperties redisProperties;

    @Autowired
    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private String maxWaitMillis;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Bean
    public JedisPool jedisPoolBuilder() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));

        return new JedisPool(jedisPoolConfig,
                redisProperties.getHost(),
                redisProperties.getPort(),
                Integer.parseInt(redisProperties.getTimeout()),
                redisProperties.getPassword(),
                redisProperties.getDatabase());
    }
}
