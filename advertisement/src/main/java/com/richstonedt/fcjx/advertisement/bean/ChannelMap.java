package com.richstonedt.fcjx.advertisement.bean;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <b><code>ChannelMap</code></b>
 * <p/>
 * 渠道名称、id 属性对象
 * <p/>
 * <b>Creation Time:</b> 2020/4/2 17:52.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Component
@ConfigurationProperties(prefix = "channel")
@EnableConfigurationProperties(ChannelMap.class)
@Data
@RefreshScope
public class ChannelMap {

    public Map<String, String> names = Maps.newHashMap();

    public Map<String, String> ids = Maps.newHashMap();

    public Map<String, String> counts = Maps.newHashMap();

    public Map<String, String> option = Maps.newHashMap();

    public String[] regions;
}
