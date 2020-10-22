package com.richstonedt.fcjx.advertisement.service;

import com.richstonedt.fcjx.advertisement.dto.AdOrderDto;
import com.richstonedt.fcjx.advertisement.dto.AdTagDto;
import com.richstonedt.fcjx.advertisement.dto.Tags;

/**
 * <b><code>TagOrderService</code></b>
 * <p/>
 * Description
 * <p/>
 * <b>Creation Time:</b> 2020/4/12 20:21.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public interface TagOrderService {

    /**
     * 获取手机号码所在标签
     *
     * @param phoneNum 手机号码
     * @return 标签信息
     */
    AdTagDto getTagInfo(String phoneNum);

    /**
     * 获取标签对应工单信息
     * @param tags 标签
     * @return 工单
     */
    AdOrderDto getOrderInfo(Tags tags);
}
