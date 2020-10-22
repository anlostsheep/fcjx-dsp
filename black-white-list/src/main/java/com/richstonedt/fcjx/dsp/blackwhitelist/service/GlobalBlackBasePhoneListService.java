/*
 * 广州丰石科技有限公司拥有本软件版权2020并保留所有权利。
 * Copyright 2020, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.richstonedt.fcjx.dsp.blackwhitelist.entity.PhoneListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.PhoneListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>GlobalBlackPhoneListService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/3/26 17:32.
 *
 * @author LIANG QING LONG
 * @since dsp-blackwhitelist
 */
@Slf4j
public class GlobalBlackBasePhoneListService extends BasePhoneListService {

    GlobalBlackBasePhoneListService(PhoneListRepository phoneListRepository, RedisTemplate<String, String> redisTemplate, Integer total, Double fpp, String key) {
        super(phoneListRepository, redisTemplate, total, fpp, key);
    }

    @Override
    protected void initBloomFilter(List<PhoneListEntity> entities) {
        log.info("开始置位全局黑白名单布隆过滤器。。。");
        List<String> keys = entities.stream().map(e -> generateBloomFilterKey(e.getPhoneNum(), GlobalBlackListService.TAG, e.getStatus())).collect(toList());
        bloomFilter.put (keys);
    }
}
