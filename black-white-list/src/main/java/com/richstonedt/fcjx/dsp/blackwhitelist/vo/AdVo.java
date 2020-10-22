package com.richstonedt.fcjx.dsp.blackwhitelist.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <b><code>AdVo</code></b>
 * <p/>
 * 客户端广告素材VO
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 17:21.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Data
@Builder
public class AdVo implements Serializable {

    private String adId;
    private String region;
    private String platform;
    private Byte status;
}
