package com.richstonedt.fcjx.dsp.common.redis;

import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.concurrent.TimeUnit;

/**
 * <b><code>RedisLock</code></b>
 * <p/>
 * 基于Redis分布式锁
 * <p/>
 * <b>Creation Time:</b> 2020/2/22 17:14.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
public class RedisLock {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisLock(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String genKey(String key) {
        return "REDIS_LOCK:" + key;
    }

    public Boolean tryLock(String key, String value, Long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(genKey(key), value, timeout, TimeUnit.MILLISECONDS);
    }

    public Boolean unlock(String key, String value) {
        //lua script
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Boolean.class);
        return redisTemplate.execute(redisScript, Lists.newArrayList(genKey(key)), value);
    }
}
