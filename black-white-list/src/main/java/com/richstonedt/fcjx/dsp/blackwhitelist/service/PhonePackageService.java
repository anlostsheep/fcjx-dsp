package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.domain.PhonePackage;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.BlackWhiteListEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <b><code>PhonePackageService</code></b>
 * <p/>
 * 解析号码包服务
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:30.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Service
@Slf4j
public class PhonePackageService {

    private final AdService adService;
    private final AdBlackWhiteListService adBlackWhiteListService;

    public PhonePackageService(AdService adService, AdBlackWhiteListService adBlackWhiteListService) {
        this.adService = adService;
        this.adBlackWhiteListService = adBlackWhiteListService;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(PhonePackage phonePackage, BasePhoneListService basePhoneListService) {
        // 保存广告素材
        List<AdEntity> adEntities = adService.buildAdEntity(phonePackage);
        adService.save(adEntities);

        // 保存广告素材对应地区
        // 为每个广告素材生成一个唯一标签，存储到号码列表实体中，用于标识每个广告素材对应那些黑白名单
        String tag = UUID.randomUUID().toString();
        // 构建黑白名单实体
        List<BlackWhiteListEntity> blackWhiteListEntities =
                adBlackWhiteListService.buildBlackWhiteListEntities(tag, phonePackage);
        adBlackWhiteListService.save(blackWhiteListEntities);

        // 保存号码
        Optional.of(phonePackage)
                // 过滤掉全量投放广告素材
                .filter( p -> !Constant.PhonePackage.OP_TYPE_ALL_LIST.equals( p.getOpType() ) )
                .map(PhonePackage::getUrl)
                // 只有存在url时，才存储号码列表
                .map( url -> basePhoneListService.buildEntity(tag, url) )
                .ifPresent(x -> basePhoneListService.save(Lists.newArrayList(x)));
    }
}
