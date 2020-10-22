package com.richstonedt.fcjx.advertisement.dao.impl;

import com.alibaba.fastjson.JSON;
import com.richstonedt.fcjx.advertisement.contants.ApiEnum;
import com.richstonedt.fcjx.advertisement.dao.AdCacheDao;
import com.richstonedt.fcjx.advertisement.domain.AdResponse;
import com.richstonedt.fcjx.advertisement.exception.SmartPushException;
import com.richstonedt.fcjx.advertisement.utils.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * <b><code>AdCacheDaoImpl</code></b>
 * <p/>
 * 广告缓存实现
 * <p/>
 * <b>Creation Time:</b> 2020/5/6 19:49.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Repository
@Slf4j
public class AdCacheDaoImpl implements AdCacheDao {

    private JedisUtils jedisUtils;

    @Autowired
    public void setJedisUtils(JedisUtils jedisUtils) {
        this.jedisUtils = jedisUtils;
    }

    @Override
    public AdResponse getAdCache(String hashName, String key) {
        String cache = jedisUtils.getHashCache(hashName, key);

        AdResponse cacheAd = null;
        if (!StringUtils.isEmpty(cache)) {
            try {
                cacheAd = JSON.parseObject(cache, AdResponse.class);
            } catch (Exception e) {
                log.error("广告缓存[{}]-[{}]转换实体失败", hashName, key, e);
                throw new SmartPushException(ApiEnum.INTERNAL_ERROR);
            }
        }

        return cacheAd;
    }

    @Override
    public void setAdCache(String hashName, String key, String value, int saveSecond) {
        jedisUtils.setHashCache(hashName, key, value, saveSecond);
    }
}
