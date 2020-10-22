package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.alibaba.fastjson.JSON;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.domain.PhonePackage;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.BlackWhiteListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.BlackWhiteListRepository;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.PhoneListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>AdBlackWhiteListService</code></b>
 * <p/>
 * 广告素材黑白名单服务
 * <p/>
 * <b>Creation Time:</b> 2020/1/14 9:55.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Service
@Slf4j
public class AdBlackWhiteListService {

    private final static String REDIS_KEY_PREFIX = "AD_BLACK_WHITE_LIST:";
    private final static int TOTAL = 1000000;
    private final static double FPP = 0.00001;

    @Autowired
    private BlackWhiteListRepository blackWhiteListRepository;
    private final BasePhoneListService basePhoneListService;
    private final RedisTemplate<String, String> redisTemplate;

    public AdBlackWhiteListService(RedisTemplate<String, String> redisTemplate, PhoneListRepository phoneListRepository) {
        this.redisTemplate = redisTemplate;
        this.basePhoneListService = new AdBlackWhiteBasePhoneListService(phoneListRepository, redisTemplate, TOTAL, FPP, REDIS_KEY_PREFIX);
    }

    public BasePhoneListService getBasePhoneListService() {
        return basePhoneListService;
    }

    public Boolean mightContain(String phoneNum, String tag, Byte status) {
        return basePhoneListService.mightContain(phoneNum, tag, status);
    }

    void save(List<BlackWhiteListEntity> entities) {
        blackWhiteListRepository.saveAll(entities);
    }

    /**
     * 重新入库
     * @param adIds
     */
    public void reset(List<String> adIds) {
        log.info("重新读取指定广告素材{}，然后入库并置位布隆过滤器", adIds);
        basePhoneListService.resetBloomFilter();
        getBlackWhiteListEntity(Constant.BlackWhiteList.STATUS_ON)
                .stream()
                .filter( e -> adIds.contains( e.getAdId() ) )
                .map(BlackWhiteListEntity::getTag)
                .map(basePhoneListService::getPhoneListEntity)
                .peek(basePhoneListService::initBloomFilter);
    }

    public List<BlackWhiteListEntity> getBlackWhiteListEntity(Byte status) {
        // 先读缓存
        List<BlackWhiteListEntity> entities = redisTemplate.execute((RedisCallback<List<BlackWhiteListEntity>>) rc -> {
            String key = genRedisKey(String.valueOf(status));
            return Objects.requireNonNull( rc.hGetAll( key.getBytes() ) )
                    .values()
                    .stream()
                    // JSON to AdEntity
                    .map(x -> JSON.<BlackWhiteListEntity>parseObject(x, BlackWhiteListEntity.class))
                    .collect(toList());
        });

        // 缓存未命中
        if ( CollectionUtils.isEmpty(entities) ) {
            entities = blackWhiteListRepository.findByStatus(status);
            saveCacheByStatus(entities);
        }

        return entities;
    }

    /**
     * 指定广告素材id获取名单实体
     * @param adIds
     * @return
     */
    public List<BlackWhiteListEntity> getBlackWhiteListEntity(List<String> adIds) {
        return blackWhiteListRepository.findByAdIdIn(adIds);
    }

    public List<BlackWhiteListEntity> getBlackWhiteListEntity(List<String> adIds, Byte status, Byte type) {
        return getBlackWhiteListEntity(status)
                .stream()
                // 过滤对应地区以及对应渠道
                .filter( x -> type.equals( x.getType() ) && adIds.contains( x.getAdId() ) )
                .collect(toList());
    }

    /**
     * 按状态缓存实体
     * @param entities
     */
    private void saveCacheByStatus(List<BlackWhiteListEntity> entities) {
        redisTemplate.executePipelined((RedisCallback<Object>) rc -> {
            entities.stream()
                    .map(BlackWhiteListEntity::getStatus)
                    .map(String::valueOf)
                    .map(this::genRedisKey)
                    .map(String::getBytes)
                    .map(rc::del);

            entities.forEach(e -> {
                Byte status = e.getStatus();
                String id = e.getId();
                String key = genRedisKey( String.valueOf(status) );
                byte[] bytes = key.getBytes();
                rc.hSet(bytes, id.getBytes(), JSON.toJSONBytes(e));
                rc.expire(bytes, 180L);
            });
            return null;
        });
    }

    /**
     * 生成Redis key
     * @param key
     * @return
     */
    private String genRedisKey(String key) {
        return REDIS_KEY_PREFIX + key;
    }

    List<BlackWhiteListEntity> buildBlackWhiteListEntities(String uuid, PhonePackage phonePackage) {
        String[] regions = phonePackage.getRegion();
        String advId = phonePackage.getAdvId();
        String opType = phonePackage.getOpType();
        Byte type = getType(opType);
        Byte status = Constant.BlackWhiteList.STATUS_ON;

        return Arrays.stream(regions)
                .map(r -> BlackWhiteListEntity.builder()
                        .region(r)
                        .adId(advId)
                        .status(status)
                        .type(type)
                        .tag(uuid)
                        .createAt(new Date())
                        .build())
                // 设置实体id
                .peek( entity -> entity.setId( String.valueOf( entity.primary() ) ) )
                .collect(toList());
    }

    private Byte getType(String opType) {
        // TODO 待改造成枚举
        byte type = Constant.BlackWhiteList.TYPE_BLACK_LIST;
        switch (opType) {
            case Constant.PhonePackage.OP_TYPE_ADD_BLACK_LIST:
                type = Constant.BlackWhiteList.TYPE_BLACK_LIST;
                break;
            case Constant.PhonePackage.OP_TYPE_ADD_WHITE_LIST:
                type = Constant.BlackWhiteList.TYPE_WHITE_LIST;
                break;
            case Constant.PhonePackage.OP_TYPE_ALL_LIST:
                type = Constant.BlackWhiteList.TYPE_ALL;
                break;
            default:
        }

        return type;
    }
}
