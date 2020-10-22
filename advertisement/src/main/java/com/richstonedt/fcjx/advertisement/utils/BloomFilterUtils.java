package com.richstonedt.fcjx.advertisement.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <b><code>BloomFilterUtils</code></b>
 * <p/>
 * BloomFilter - 布隆过滤器
 * <p/>
 * <b>Creation Time:</b> 2020/4/29 12:52.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be
 */
@Slf4j
@Component
public class BloomFilterUtils {

    private static final String DSP_BLOOM_FILTER_NAME = "DSP:DSP_BLOOM_FILTER:";
    private RedissonClient redissonClient;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RBloomFilter<Object> initBloomFilter(String bloomFilterName) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(DSP_BLOOM_FILTER_NAME + bloomFilterName);

        bloomFilter.tryInit(500000L, 0.03);
        return bloomFilter;
    }

    public RBloomFilter<Object> initBloomFilter(String bloomFilterName, Long capacity, Double falseProbability) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(DSP_BLOOM_FILTER_NAME + bloomFilterName);

        bloomFilter.tryInit(capacity, falseProbability);
        return bloomFilter;
    }

    public RBloomFilter<Object> getBloomFilter(String bloomFilterName) {
        return redissonClient.getBloomFilter(DSP_BLOOM_FILTER_NAME + bloomFilterName);
    }

    public void putData(String bloomFilterName, List<Object> data) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(DSP_BLOOM_FILTER_NAME + bloomFilterName);

        data.forEach(bloomFilter::add);
    }

    public boolean contains(String bloomFilterName, Object point) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(DSP_BLOOM_FILTER_NAME + bloomFilterName);

        return bloomFilter.contains(point);
    }

    public void cleanUpBloomFilter(String bloomFilterName) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(DSP_BLOOM_FILTER_NAME + bloomFilterName);
        bloomFilter.delete();
    }
}
