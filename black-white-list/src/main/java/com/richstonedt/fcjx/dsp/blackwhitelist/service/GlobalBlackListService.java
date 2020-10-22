package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.PhoneListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.PhoneListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>AllBlackListService</code></b>
 * <p/>
 * 全局黑名单服务
 * <p/>
 * <b>Creation Time:</b> 2020/1/14 10:06.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Service
@Slf4j
public class GlobalBlackListService {

    /**
     * 全局黑名单标签标识
     */
    public final static String TAG = Constant.BlackWhiteList.GLOBAL_BLACK_LIST_TAG;
    private final static int TOTAL = 3000000;
    private final static double FPP = 0.0000001;
    /**
     * Redis Key
     */
    private final static String REDIS_KEY_PREFIX = "GLOBAL_BLACK_LIST:";
    /**
     * Phone Num Hash of Redis Key
     */
    private final static String REDIS_KEY_PHONE_NUM = REDIS_KEY_PREFIX + "NUM";
    private final RedisTemplate<String, String> redisTemplate;
    private final BasePhoneListService basePhoneListService;

    public GlobalBlackListService(RedisTemplate<String, String> redisTemplate, PhoneListRepository phoneListRepository) {
        this.redisTemplate = redisTemplate;
        this.basePhoneListService = new GlobalBlackBasePhoneListService(phoneListRepository, redisTemplate, TOTAL, FPP, REDIS_KEY_PREFIX);
    }

    public Boolean mightContain(String phoneNum, String tag, Byte status) {
        return basePhoneListService.mightContain(phoneNum, tag, status);
    }

    private void reset() {
        log.info("开始重置全局黑名单的布隆过滤器。。。");
        int i = 0;
        int size = 20000;
        List<PhoneListEntity> entities = Lists.newArrayList();
        basePhoneListService.resetBloomFilter();
        do {
            entities = getPhoneListEntity(PageRequest.of(i++, size));
            basePhoneListService.initBloomFilter(entities);
        } while (entities.size() >= size);
    }

    /**
     * 判断指定号码是否在黑名单中
     * @param phoneNum
     * @param status
     * @return
     */
    public Boolean contain(String phoneNum, Byte status) {
        // 从缓存中读数据
        List<PhoneListEntity> result = getCache(Lists.newArrayList(phoneNum))
                .stream()
                .filter(e -> Constant.BlackWhiteList.GLOBAL_BLACK_LIST_TAG.equals(e.getTag()) && status.equals(e.getStatus()))
                .collect(toList());
        return !CollectionUtils.isEmpty( result );
    }

    /**
     * 入库以及缓存
     * @param phoneNums
     * @param status
     */
    public void save(List<String> phoneNums, Byte status) {
        List<PhoneListEntity> entities = getPhoneListEntity(phoneNums)
                .stream()
                .peek( entity -> entity.setStatus(status) )
                .peek( entity -> entity.setUpdateAt( new Date() ) )
                .collect(toList());
        // 已入库号码
        List<String> existPhoneNums = entities.stream().map(PhoneListEntity::getPhoneNum).collect(toList());
        // 过滤出未入库号码实体
        List<String> notExistPhoneNums = phoneNums.stream().filter(n -> !existPhoneNums.contains(n)).collect(toList());
        // 创建未入库号码实体
        entities.addAll(buildGobalPhoneListEntity(notExistPhoneNums, status));
        basePhoneListService.save(entities);
        saveCache(entities);
    }

    /**
     * 指定号码，获取实体
     * @param phoneNums
     * @return
     */
    public List<PhoneListEntity> getPhoneListEntity(List<String> phoneNums) {
        List<PhoneListEntity> entities = getCache(phoneNums);

        //缓存未命中
        if ( CollectionUtils.isEmpty(entities) || entities.size() != phoneNums.size() ) {
            entities = basePhoneListService.getPhoneListEntity(phoneNums);
            saveCache(entities);
        }
        return entities;
    }

    public List<PhoneListEntity> buildGobalPhoneListEntity(List<String> phoneNums, Byte status) {
        return basePhoneListService.buildEntity(phoneNums, status, TAG);
    }

    /**
     * 读缓存
     * @param phoneNums
     * @return
     */
    private List<PhoneListEntity> getCache(List<String> phoneNums) {
        return redisTemplate.execute((RedisCallback<List<PhoneListEntity>>) rc -> {
            // 按号码查hash
            return phoneNums.stream()
                        .map(n -> rc.hGet(REDIS_KEY_PHONE_NUM.getBytes(), n.getBytes()))
                        .filter(Objects::nonNull)
                        .map(v -> JSON.<PhoneListEntity>parseObject(v, PhoneListEntity.class))
                        .collect(toList());
        });
    }

    /**
     * 刷新缓存
     */
    public void refreshCache() {
        reset();
    }

    /**
     * 获取所有全局黑名单实体，带分页
     * @param pageRequest
     * @return
     */
    public List<PhoneListEntity> getPhoneListEntity(PageRequest pageRequest) {
        return basePhoneListService.getPhoneListEntity(Constant.BlackWhiteList.GLOBAL_BLACK_LIST_TAG, pageRequest);
    }

    /**
     * 缓存
     * @param entities
     */
    private void saveCache(List<PhoneListEntity> entities) {
        redisTemplate.executePipelined((RedisCallback<Object>) rc -> {
            entities.forEach(e -> {
                byte[] bytes = REDIS_KEY_PHONE_NUM.getBytes();
                rc.hSet( bytes, e.getPhoneNum().getBytes(), JSON.toJSONBytes(e) );
                rc.expire(bytes, 5*60L);
            });
            return null;
        });
    }
}
