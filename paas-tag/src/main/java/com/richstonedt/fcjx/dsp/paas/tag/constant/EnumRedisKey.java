package com.richstonedt.fcjx.dsp.paas.tag.constant;

import lombok.Getter;

@Getter
public enum EnumRedisKey {

    /**
     * 定时读取PAAS标签分布式锁redis key
     */
    PAAS_SCHEDULED_SERVICE_REDIS_LOCK("PAAS:SCHEDULED_SERVICE_READ:REDIS_LOCK"),

    /**
     * paas标签redis key
     */
    PAAS_TAG("PAAS:TAG"),

    /**
     * PAAS标签布隆过滤器redis key
     */
    PAAS_BLOOM_FILTER("PAAS:BLOOM_FILTER"),

    PAAS_DOWLOADED_FILES("PAAS:DOWLOADED:FILES");

    private String key;

    EnumRedisKey(String key) {
        this.key = key;
    }

    public String getKey(String suffix) {
        return key + ":" + suffix;
    }
}
