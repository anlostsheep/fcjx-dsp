package com.richstonedt.fcjx.advertisement.service;

import com.google.common.collect.Maps;
import com.richstonedt.fcjx.advertisement.dao.MobileLocalMapper;
import com.richstonedt.fcjx.advertisement.domain.MobileLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <b><code>MoblieLocalRunner</code></b>
 * <p/>
 * 项目启动初始化地市数据
 * <p/>
 * <b>Creation Time:</b> 2020/5/11 14:33.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@Component
public class MoblieLocalRunner implements ApplicationRunner {

    public static final Map<String, String> MOBILE_LOCAL_DATA_MAP = Maps.newConcurrentMap();

    private MobileLocalMapper mobileLocalMapper;

    @Autowired
    public void setMobileLocalMapper(MobileLocalMapper mobileLocalMapper) {
        this.mobileLocalMapper = mobileLocalMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化加载地市表数据执行...");
        
        List<MobileLocal> all = mobileLocalMapper.findAll();
        log.info("初始化加载地市表数据完成,已加载[{}]行数据", all.size());
        
        all.forEach(e -> MOBILE_LOCAL_DATA_MAP.put(e.getNumberSegment(), e.getAreaAlias()));
    }
}
