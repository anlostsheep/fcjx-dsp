package com.richstonedt.fcjx.advertisement.dao;

import com.richstonedt.fcjx.advertisement.domain.AdResponse;

/**
 * <b><code>AdCacheDao</code></b>
 * <p/>
 * 广告缓存抽象
 * <p/>
 * <b>Creation Time:</b> 2020/5/6 19:44.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public interface AdCacheDao {

    /**
     * 获取广告缓存
     *
     * @param hashName hash 表名
     * @param key      hash 表字段名
     * @return cache
     */
    AdResponse getAdCache(String hashName, String key);

    /**
     * 设置广告缓存
     *
     * @param hashName   hash 表名
     * @param key        hash 表字段名
     * @param value      value 值
     * @param saveSecond 缓存时间
     */
    void setAdCache(String hashName, String key, String value, int saveSecond);
}
