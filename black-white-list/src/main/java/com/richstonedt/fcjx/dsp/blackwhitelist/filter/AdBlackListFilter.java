package com.richstonedt.fcjx.dsp.blackwhitelist.filter;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.BlackWhiteListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.AdBlackWhiteListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>AdBlackListFilter</code></b>
 * <p/>
 * 广告素材黑名单过滤器
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 15:54.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Component
@Slf4j
public class AdBlackListFilter extends BaseAdFilter {

    @Autowired
    private AdBlackWhiteListService adBlackWhiteListService;

    @Override
    public MyResponseEntity<List<AdEntity>> processRequest(List<AdEntity> adEntities, String phoneNum) {
        log.info("正在进入广告素材黑名单过滤器。。。请求参数{}, {}", adEntities, phoneNum);
        // 只获取有黑名单的广告素材
        List<String> adIds = adEntities.stream()
                .map(AdEntity::getAdId)
                .collect(toList());
        List<BlackWhiteListEntity> blackWhiteListEntities =
                adBlackWhiteListService.getBlackWhiteListEntity(adIds, Constant.BlackWhiteList.STATUS_ON, Constant.BlackWhiteList.TYPE_BLACK_LIST);

        // 判断指定手机号码是否在黑名单中。若存在，则将请求交给下一个过滤器处理，否则返回所有广告素材
        List<String> illegalAdIds = blackWhiteListEntities.stream()
                // 取出tag，判断是否在黑名单中
                .filter(e -> adBlackWhiteListService.mightContain(phoneNum, e.getTag(), Constant.PhoneList.STATUS_ON))
                // 过滤出黑名单包含该号码的广告素材id
                .map(BlackWhiteListEntity::getAdId)
                .collect(toList());

        // 过滤掉在黑名单中的广告素材
        List<AdEntity> legalAdEntities = adEntities.stream()
                .filter(e -> !illegalAdIds.contains(e.getAdId()))
                .collect(toList());

        // 号码在广告素材黑名单中(即过滤掉所有在黑名单中的广告素材后，已经没有剩余的广告素材了)
        MyResponseCode code = MyResponseCode.PHONE_NUM_IN_AD_BLACK_LIST;
        if ( CollectionUtils.isEmpty(legalAdEntities) ) {
            log.info("号码{}在广告素材黑名单中", phoneNum);
            return new MyResponseEntity<>(code.getCode(), code.getMsg(), Lists.newArrayList());
        }

        // 传递不在黑名单中的广告素材给下一个过滤器处理
        return successor.processRequest(legalAdEntities, phoneNum);
    }
}
