package com.richstonedt.fcjx.dsp.blackwhitelist.filter;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.GlobalBlackListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <b><code>AllBlackListFilter</code></b>
 * <p/>
 * 全局黑名单过滤器
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 15:59.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Component
@Slf4j
public class GlobalBlackListFilter extends BaseAdFilter {

    @Autowired
    private GlobalBlackListService globalBlackListService;

    /**
     * 号码在黑名单中，则过滤器不再往下走，否则交个下一个过滤器处理
     * @param adEntities
     * @param phoneNum
     * @return
     */
    @Override
    public MyResponseEntity<List<AdEntity>> processRequest(List<AdEntity> adEntities, String phoneNum) {
        log.info("正在进入全局黑名单过滤器。。。请求参数{}, {}", adEntities, phoneNum);
        MyResponseCode code = MyResponseCode.PHONE_NUM_IN_ALL_BLACK_LIST;
        Boolean contain = globalBlackListService.mightContain(phoneNum, GlobalBlackListService.TAG, Constant.PhoneList.STATUS_ON);
        // 号码在全局黑名单中
        if (contain) {
            log.info("号码{}在全局黑名单中", phoneNum);
            return new MyResponseEntity<>(code.getCode(), code.getMsg(), Lists.newArrayList());
        }
        return successor.processRequest(adEntities, phoneNum);
    }
}
