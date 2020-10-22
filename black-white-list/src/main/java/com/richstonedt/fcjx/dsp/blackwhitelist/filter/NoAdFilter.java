/*
 * 广州丰石科技有限公司拥有本软件版权2020并保留所有权利。
 * Copyright 2020, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.fcjx.dsp.blackwhitelist.filter;

import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <b><code>NoAdFilter</code></b>
 * <p/>
 * 所有广告素材全部下线过滤器
 * 单独使用一个过滤器来处理，是为了之后某些场景下，
 * 这种情况可能需要某种特殊处理
 * <p/>
 * <b>Creation Time:</b> 2020/3/14 12:35.
 *
 * @author LIANG QING LONG
 * @since dsp-blackwhitelist
 */
@Component
@Slf4j
public class NoAdFilter extends BaseAdFilter {

    @Override
    public MyResponseEntity<List<AdEntity>> processRequest(List<AdEntity> adEntities, String phoneNum) {
        log.info("正在进入所有广告素材全部下线过滤器。。。请求参数{}, {}", adEntities, phoneNum);
        MyResponseCode code = MyResponseCode.ALL_ADS_OFFLINE;
        return CollectionUtils.isEmpty(adEntities) ?
                new MyResponseEntity<>(code.getCode(), code.getMsg(), Lists.newArrayList()) :
                successor.processRequest(adEntities, phoneNum);
    }
}
