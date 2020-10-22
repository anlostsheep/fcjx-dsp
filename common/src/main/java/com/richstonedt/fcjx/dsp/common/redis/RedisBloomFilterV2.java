/*
 * 广州丰石科技有限公司拥有本软件版权2020并保留所有权利。
 * Copyright 2020, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.fcjx.dsp.common.redis;

import com.google.common.base.Preconditions;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Objects;

/**
 * <b><code>RedisBloomFilterV2</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/4 6:04 下午.
 *
 * @author LIANG QING LONG
 * @since dsp-blackwhitelist
 */
public class RedisBloomFilterV2<A, B, T> {

    private final RedisTemplate<A, B> redisTemplate;
    private final BloomFilterHelper<T> bloomFilterHelper;

    public RedisBloomFilterV2(RedisTemplate<A, B> redisTemplate, BloomFilterHelper<T> bloomFilterHelper) {
        this.redisTemplate = redisTemplate;
        this.bloomFilterHelper = bloomFilterHelper;
    }

    /**
     * 根据给定的布隆过滤器添加值
     * @param key
     * @param value
     */
    public void put(String key, T value) {
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            put(key, value, redisConnection);
            return null;
        });
    }

    public void put(String key, List<T> values) {
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            for (T value : values) {
                put(key, value, redisConnection);
            }
            return null;
        });
    }

    private void put (String key, T value, RedisConnection redisConnection) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");

        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisConnection.setBit(key.getBytes(), i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     * @param key
     * @param value
     * @return
     */
    public Boolean mightContain(String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");

        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            for (int i : offset) {
                Boolean flag = redisConnection.getBit(key.getBytes(), i);
                if (Objects.equals(flag, false)) {
                    return false;
                }
            }
            return true;
        });
    }
}
