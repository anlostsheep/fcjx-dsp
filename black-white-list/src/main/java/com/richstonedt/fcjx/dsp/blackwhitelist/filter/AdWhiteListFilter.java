package com.richstonedt.fcjx.dsp.blackwhitelist.filter;

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
 * <b><code>AdWhiteListFilter</code></b>
 * <p/>
 * 广告素材白名单过滤器
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 15:58.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Component
@Slf4j
public class AdWhiteListFilter extends BaseAdFilter {

    @Autowired
    private AdBlackWhiteListService adBlackWhiteListService;

    @Override
    public MyResponseEntity<List<AdEntity>> processRequest(List<AdEntity> adEntities, String phoneNum) {
        log.info("正在进入广告素材白名单过滤器。。。请求参数{}, {}", adEntities, phoneNum);
        // 获取有白名单的广告素材
        List<String> adIds = adEntities.stream()
                .map(AdEntity::getAdId)
                .collect(toList());
        List<BlackWhiteListEntity> whiteListEntities =
                adBlackWhiteListService.getBlackWhiteListEntity(adIds, Constant.BlackWhiteList.STATUS_ON, Constant.BlackWhiteList.TYPE_WHITE_LIST);

        // 有白名单素材id
        List<String> whiteListAdId = whiteListEntities.stream()
                .map(BlackWhiteListEntity::getAdId)
                .collect(toList());

        // 若手机号码在白名单中，则返回所有广告素材，否则将请求交给下一个过滤器处理
        List<String> legalAdIds = whiteListEntities.stream()
                // 取出tag，判断是否在白名单中
                .filter(e -> adBlackWhiteListService.mightContain(phoneNum, e.getTag(), Constant.PhoneList.STATUS_ON))
                // 过滤出白名单包含该号码的广告素材id
                .map(BlackWhiteListEntity::getAdId)
                .collect(toList());

        // 号码在广告素材白名单中
        if ( !CollectionUtils.isEmpty(legalAdIds) ) {
            log.info("号码{}在广告素材白名单中", phoneNum);
            MyResponseCode code = MyResponseCode.PHONE_NUM_IN_AD_WHITE_LIST;
            return new MyResponseEntity<>(code.getCode(), code.getMsg(), adEntities);
        }

        // 如果不命中白名单
        // 将排除掉有白名单的广告素材实体，传递给下个过滤器处理
        // 过滤出包含该号码的广告素材实体
        // 排除掉有白名单的广告素材，剩余广告素材要么是全量，要么是只有黑名单
        // 作用就是白名单优先，有白名单时，黑名单失效
        List<AdEntity> adEntitiesExcludeWhiteList = adEntities.stream()
                .filter(e -> !whiteListAdId.contains(e.getAdId()))
                .collect(toList());
        return successor.processRequest(adEntitiesExcludeWhiteList, phoneNum);
    }
}
