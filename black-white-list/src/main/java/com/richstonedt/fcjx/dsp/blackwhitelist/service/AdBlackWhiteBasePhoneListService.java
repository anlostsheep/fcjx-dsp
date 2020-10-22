/*
 * 广州丰石科技有限公司拥有本软件版权2020并保留所有权利。
 * Copyright 2020, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.PhoneListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.PhoneListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>AdBlackWhitePhoneListService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/3/26 17:29.
 *
 * @author LIANG QING LONG
 * @since dsp-blackwhitelist
 */
@Slf4j
public class AdBlackWhiteBasePhoneListService extends BasePhoneListService {

    AdBlackWhiteBasePhoneListService(PhoneListRepository phoneListRepository, RedisTemplate<String, String> redisTemplate, Integer total, Double fpp, String key) {
        super(phoneListRepository, redisTemplate, total, fpp, key);
    }

    @Override
    protected void initBloomFilter(List<PhoneListEntity> entities) {
        // 生产布隆过滤器键值, 置位布隆过滤器
        log.info("开始置位广告素材的黑白名单布隆过滤器。。。");
        entities.forEach( e -> bloomFilter.put (genKeys(e) ) );
    }

    private List<String> genKeys(PhoneListEntity entity) {
        String tag = entity.getTag();
        Byte status = entity.getStatus();
        String url = entity.getUrl();
        String phoneNum = entity.getPhoneNum();

        // 若实体有手机号，不再解析url
        if ( !Strings.isNullOrEmpty(phoneNum) ) {
            String key = generateBloomFilterKey(phoneNum, tag, status);
            bloomFilter.put(key);
            return Lists.newArrayList();
        }

        return Optional.of(url)
                // 解析出号码
                .map(this::parsingByUrl)
                .get()
                .stream()
                // 生成布隆过滤器键值
                .map(n -> generateBloomFilterKey(n, tag, status))
                .collect(toList());
    }
}
