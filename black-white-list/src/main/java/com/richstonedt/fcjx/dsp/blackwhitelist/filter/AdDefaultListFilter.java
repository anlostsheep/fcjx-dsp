package com.richstonedt.fcjx.dsp.blackwhitelist.filter;

import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <b><code>DeffaultAdFilter</code></b>
 * <p/>
 * 广告素材默认过滤器
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 16:58.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
@Component
@Slf4j
public class AdDefaultListFilter extends BaseAdFilter {

    @Override
    public MyResponseEntity<List<AdEntity>> processRequest(List<AdEntity> adEntities, String phoneNum) {
        log.info("正在进入默认过滤器。。。请求参数{}, {}", adEntities, phoneNum);
        MyResponseCode code = MyResponseCode.DEFAULT_AD;
        return new MyResponseEntity<>(code.getCode(), code.getMsg(), adEntities);
    }
}
