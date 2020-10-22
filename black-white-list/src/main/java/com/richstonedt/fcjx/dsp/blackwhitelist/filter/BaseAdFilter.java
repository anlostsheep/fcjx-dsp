package com.richstonedt.fcjx.dsp.blackwhitelist.filter;

import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;

import java.util.List;

/**
 * <b><code>AdFilter</code></b>
 * <p/>
 * 广告素材过滤器基类
 * <p/>
 * <b>Creation Time:</b> 2020/1/9 10:04.
 *
 * @author liangqinglong
 * @since smartpush-cmgddr-parent
 */
public abstract class BaseAdFilter {

    BaseAdFilter successor;

    /**
     * 后继过滤器
     * @param successor
     */
    public void setSuccessor(BaseAdFilter successor) {
        this.successor = successor;
    }

    /**
     * 请求具体处理
     * @return
     */
    public abstract MyResponseEntity<List<AdEntity>> processRequest(List<AdEntity> adEntities, String phoneNum);
}
