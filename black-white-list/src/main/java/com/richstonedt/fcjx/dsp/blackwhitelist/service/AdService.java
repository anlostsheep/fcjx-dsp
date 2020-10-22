package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.blackwhitelist.domain.PhonePackage;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.BlackWhiteListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.exception.ApiException;
import com.richstonedt.fcjx.dsp.blackwhitelist.filter.BaseAdFilter;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.AdRepository;
import com.richstonedt.fcjx.dsp.blackwhitelist.vo.AdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>AdService</code></b>
 * <p/>
 * 广告素材服务
 * <p/>
 * <b>Creation Time:</b> 2020/1/14 9:55.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Service
@Slf4j
public class AdService {

    private final static String REDIS_KEY_PREFIX = "AD_ENTITY:";
    @Autowired
    private AdBlackWhiteListService adBlackWhiteListService;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    @Qualifier("adBlackListFilter")
    private BaseAdFilter adBlackListFilter;
    @Autowired
    @Qualifier("globalBlackListFilter")
    private BaseAdFilter globalBlackListFilter;
    @Autowired
    @Qualifier("adWhiteListFilter")
    private BaseAdFilter adWhiteListFilter;
    @Autowired
    @Qualifier("adDefaultListFilter")
    private BaseAdFilter defaultBaseAdFilter;
    @Autowired
    @Qualifier("noAdFilter")
    private BaseAdFilter noAdFilter;
    private final RedisTemplate<String, String> redisTemplate;

    public AdService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void up() {
        List<String> adIds = getAdEntity(Constant.Ad.STATUS_ON)
                .stream()
                .map(AdEntity::getAdId)
                .collect(toList());
        adBlackWhiteListService.reset(adIds);
    }

    /**
     * 上下线广告素材
     * @param adId
     * @param status
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateAdEntity(String adId, Byte status) {
        // 读取广告素材实体
        List<AdEntity> entities = getAdEntity(Lists.newArrayList(adId));
        entities.forEach(e -> e.setUpdateAt(new Date()));

        // 读取名单实体
        List<BlackWhiteListEntity> blackWhiteListEntities = adBlackWhiteListService.getBlackWhiteListEntity(Lists.newArrayList(adId));

        switch (status) {
            // 广告重新上线，得重新解析一次url置位布隆过滤器
            case Constant.Ad.STATUS_ON:
                log.info("上线广告素材{}", adId);
                // 更新广告素材状态
                entities.forEach(e -> e.setStatus(Constant.Ad.STATUS_ON));
                // 更新名单状态
                blackWhiteListEntities.forEach(e -> e.setStatus(Constant.BlackWhiteList.STATUS_ON));
                adBlackWhiteListService.reset( Lists.newArrayList(adId) );
                break;
            case Constant.Ad.STATUS_OFF:
                log.info("下线广告素材{}", adId);
                // 更新广告素材状态
                entities.forEach(e -> e.setStatus(Constant.Ad.STATUS_OFF));
                // 更新名单状态
                blackWhiteListEntities.forEach(e -> e.setStatus(Constant.BlackWhiteList.STATUS_OFF));
                break;
            default:
                MyResponseCode responseCode = MyResponseCode.INVALID_PARAMETER;
                throw new ApiException(responseCode.getCode(), responseCode.getMsg(), Lists.newArrayList());
        }

        // 更新广告素材实体
        save(entities);
        // 更新名单实体
        adBlackWhiteListService.save(blackWhiteListEntities);
    }

    /**
     * 获取广告素材（走过滤器）
     * @return
     * @throws ApiException
     */
    public MyResponseEntity<List<AdEntity>> getAdEntityViaFiler(List<AdEntity> entities, String phoneNum) {
        // 手机号码 ==> 全局黑名单 ==> 所有广告素材全部下线过滤器 ==> 广告素材黑名单 ==> 广告素材白名单 ==> 广告素材默认名单（返回默认广告素材）
        globalBlackListFilter.setSuccessor(noAdFilter);
        noAdFilter.setSuccessor(adBlackListFilter);
        adBlackListFilter.setSuccessor(adWhiteListFilter);
        adWhiteListFilter.setSuccessor(defaultBaseAdFilter);
        return globalBlackListFilter.processRequest(entities, phoneNum);
    }

    /**
     * 获取广告素材（走过滤器）
     * @param platform
     * @param region
     * @param phoneNum
     * @return
     * @throws ApiException
     */
    public MyResponseEntity<List<AdEntity>> getAdEntityViaFiler(String platform, String region, String phoneNum) {
        // 根据广告渠道和地区查出所有广告素材
        List<AdEntity> entities = getAdEntity(region, platform, Constant.Ad.STATUS_ON);
        return getAdEntityViaFiler(entities, phoneNum);
    }

    /**
     * 获取广告素材（走过滤器）
     * @param platform
     * @param region
     * @param phoneNum
     * @return
     * @throws ApiException
     */
    public MyResponseEntity<List<AdEntity>> getAdEntityViaFiler(String platform, String region, String phoneNum, List<String> adIds) {
        // 根据广告渠道和地区以及指定广告素材id查出所有广告素材
        List<AdEntity> entities = getAdEntity(adIds);
        List<AdEntity> adEntities = entities.stream()
                .filter(x -> Objects.equals(platform, x.getPlatform()))
                .filter(x -> Objects.equals(region, x.getRegion()))
                .collect(toList());
        return getAdEntityViaFiler(adEntities, phoneNum);
    }

    /**
     * 获取指定渠道，指定地区，指定状态的广告素材
     * @param region
     * @param platform
     * @param status
     * @return
     */
    public List<AdEntity> getAdEntity(String region, String platform, Byte status) {
        return getAdEntity(status)
                .stream()
                .filter( x -> region.equals( x.getRegion() ) && platform.equals( x.getPlatform() ) )
                .collect( toList() );
    }

    /**
     * 指定id获取广告素材
     * @param adIds
     * @return
     */
    public List<AdEntity> getAdEntity(List<String> adIds) {
        return adRepository.findByAdIdIn(adIds);
    }

    public List<AdEntity> getAdEntity(Byte status) {
        // 先读缓存
        List<AdEntity> entities = redisTemplate.execute((RedisCallback<List<AdEntity>>) rc -> {
            String key = genRedisKey(String.valueOf(status));
            return Objects.requireNonNull( rc.hGetAll( key.getBytes() ) )
                    .values()
                    .stream()
                    // JSON to AdEntity
                    .map(x -> JSON.<AdEntity>parseObject(x, AdEntity.class))
                    .collect(toList());
        });

        // 缓存未命中
        if ( CollectionUtils.isEmpty(entities) ) {
            // 获取所有指定状态的广告素材
            entities = getAdEntityByStatusFromDb(status);
            // 缓存数据
            saveCacheByStatus(entities);
        }

        return entities;
    }

    /**
     * 根据状态从数据库中获取广告素材，带分页
     * @param status
     * @param pageRequest
     * @return
     */
    public Page<AdEntity> getAdEntityByStatusFromDb(Byte status, PageRequest pageRequest) {
        return adRepository.findByStatus(status, pageRequest);
    }

    /**
     * 根据状态从数据库中获取广告素材
     * @param status
     * @return
     */
    public List<AdEntity> getAdEntityByStatusFromDb(Byte status) {
        return adRepository.findByStatus(status);
    }

    /**
     * 保存广告素材
     * @param entities
     */
    public void save(List<AdEntity> entities) {
        adRepository.saveAll(entities);
    }

    /**
     * 按状态缓存广告素材实体
     * @param entities
     */
    private void saveCacheByStatus(List<AdEntity> entities) {
        redisTemplate.executePipelined((RedisCallback<Object>) rc -> {
            entities.stream()
                    .map(AdEntity::getStatus)
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

    /**
     * 封装广告素材vo类
     * @param entities
     * @return
     */
    public List<AdVo> buildAdVo(List<AdEntity> entities) {
        return entities.stream()
                .map(e -> AdVo.builder()
                        .adId(e.getAdId())
                        .platform(e.getPlatform())
                        .region(e.getRegion())
                        .status(e.getStatus())
                        .build())
                .collect(toList());
    }

    List<AdEntity> buildAdEntity(PhonePackage phonePackage) {
        String[] region = phonePackage.getRegion();
        String advId = phonePackage.getAdvId();
        Byte status = Constant.Ad.STATUS_ON;
        String platform = phonePackage.getPlatform();
        return Arrays.stream(region)
                .map(r -> AdEntity.builder()
                        .region(r)
                        .adId(advId)
                        .status(status)
                        .platform(platform)
                        .createAt(new Date())
                        .updateAt(new Date())
                        .build())
                // 设置实体id
                .peek(entity -> entity.setId( String.valueOf( entity.primary() ) ) )
                .collect(Collectors.toList());
    }
}
