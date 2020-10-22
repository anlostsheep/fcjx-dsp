package com.richstonedt.fcjx.dsp.blackwhitelist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <b><code>PhonePackage</code></b>
 * <p/>
 * 号码包POJO，包含广告素材id和黑白单
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 10:56.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhonePackage implements Serializable {

    /**
     * 上传过来黑白名单url，
     * 由哇棒的系统生成，
     * 其实就是一个文本文件
     */
    private String url;
    /**
     * 广告素材id
     */
    private String advId;
    /**
     * 作用未知，
     * 去问江国礼吧
     */
    private String method;
    /**
     * 操作类型，
     * 区分什么时候添加广告素材白名单，
     * 什么时候添加广告素材黑名单，
     * 什么时候添加全局黑名单,
     * 为啥这么坑爹，去问江国礼吧
     */
    private String opType;
    /**
     * 广告素材的渠道
     */
    private String platform;
    /**
     * 地区，广州啊，北京啊，西雅图啊
     */
    private String[] region;

    /**
     * 状态
     */
    private Byte status;
}
