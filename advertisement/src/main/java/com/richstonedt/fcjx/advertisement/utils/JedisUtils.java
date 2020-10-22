package com.richstonedt.fcjx.advertisement.utils;

import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <b><code>JedisUtils</code></b>
 * <p/>
 * Jedis 操作 redis 工具类(原生 api)
 * <p/>
 * <b>Creation Time:</b> 2020/5/1 22:09.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component
@Slf4j
public class JedisUtils {

    private JedisPool jedisPool;

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setStringCache(String key, String value, int cacheSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, cacheSeconds, value);
        } catch (Exception e) {
            log.error("设置 string 缓存[{}]出错", key, e);
        }
    }

    public String getStringCache(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Boolean exists = jedis.exists(key);

            String result = null;
            if (exists) {
                result = jedis.get(key);

            }

            return result;
        } catch (Exception e) {
            log.error("获取 string 缓存[{}]出错", key, e);
            throw new SmartPushException(ApiEnum.INTERNAL_ERROR);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setHashCache(String hashName, String key, String value, int cacheSeconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(hashName, key, value);
            jedis.expire(hashName, cacheSeconds);
        } catch (Exception e) {
            log.error("设置 hash 缓存[{}]-[{}]出错", hashName, key, e);
            throw new SmartPushException(ApiEnum.INTERNAL_ERROR);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String getHashCache(String hashName, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();

            String result = null;
            Boolean hexists = jedis.hexists(hashName, key);
            if (hexists) {
                result = jedis.hget(hashName, key);
            }

            return result;
        } catch (Exception e) {
            log.error("获取 hash 缓存[{}]-[{}] 时出错", hashName, key, e);
            throw new SmartPushException(ApiEnum.INTERNAL_ERROR);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
