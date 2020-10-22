package com.richstonedt.fcjx.dsp.blackwhitelist.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.AdEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.AdService;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.BaseService;
import com.richstonedt.fcjx.dsp.blackwhitelist.vo.AdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Objects;

/**
 * <b><code>AdController</code></b>
 * <p/>
 * 广告素材API
 * <p/>
 * <b>Creation Time:</b> 2020/1/14 10:10.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@RestController
@RequestMapping("/ad")
@Slf4j
public class AdController {

    @Autowired
    private AdService adService;
    @Autowired
    private BaseService baseService;

    /**
     * 根据状态获取广告素材
     * @param status
     * @return
     */
    @RequestMapping(value = "/status/{status}/{page}/{size}", method = RequestMethod.GET)
    public MyResponseEntity get(@PathVariable("status") Byte status,
                                @PathVariable("page") Integer page,
                                @PathVariable("size") Integer size) {
        // 请求参数校验
        Assert.isTrue( !Objects.isNull(status), "状态码不能为空" );
        Assert.isTrue( !Objects.isNull(page) && page > 0, "当前页" );
        Assert.isTrue( !Objects.isNull(size) && size > 0, "页数" );
        Page<AdEntity> entityPage = adService.getAdEntityByStatusFromDb(status, PageRequest.of(page-1, size));
        return new MyResponseEntity<>().success(entityPage.getContent());
    }

    /**
     * 广告素材上线接口
     * @param adId
     * @param status
     * @return
     */
    @RequestMapping(value = "/update/{adId}/{status}", method = RequestMethod.POST)
    public MyResponseEntity offAd(@PathVariable("adId") String adId,
                                  @PathVariable("status") Byte status) {
        // 请求参数校验
        Assert.isTrue( !Strings.isNullOrEmpty(adId), "广告素材id不能为空" );
        Assert.isTrue( !Objects.isNull(status), "状态码不能为空" );

        adService.updateAdEntity(adId, status);
        return new MyResponseEntity<>().success(Lists.newArrayList());
    }

    /**
     * 取广告素材id接口
     * @param platform
     * @param region
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/{platform}/{region}/{phoneNum}", method = RequestMethod.GET)
    public DeferredResult<MyResponseEntity> getAds(@PathVariable("platform") String platform,
                                                   @PathVariable("region") String region,
                                                   @PathVariable("phoneNum") String phoneNum) {
        // 请求参数校验
        Assert.isTrue( !Strings.isNullOrEmpty(platform), "广告渠道不能为空" );
        Assert.isTrue( !Strings.isNullOrEmpty(region), "地区不能为空" );
        Assert.isTrue( !Strings.isNullOrEmpty(phoneNum), "手机号码不能为空" );

        // 异步处理取广告素材请求
        return baseService.processRequestAsync(5000L, () -> {
            MyResponseEntity<List<AdEntity>> responseEntity = adService.getAdEntityViaFiler(platform, region, phoneNum);
            log.info("获取广告素材调用结果：{}", responseEntity);
            Integer code = responseEntity.getCode();
            String desc = responseEntity.getDesc();
            List<AdVo> adVos = adService.buildAdVo(responseEntity.getData());
            return new MyResponseEntity<>(code, desc, adVos);
        });
    }

    /**
     * 取广告素材id接口
     * @param platform
     * @param region
     * @param phoneNum
     * @return
     */
    @RequestMapping(value = "/id/{platform}/{region}/{phoneNum}", method = RequestMethod.GET)
    public DeferredResult<MyResponseEntity> getAdById(@PathVariable("platform") String platform,
                                                      @PathVariable("region") String region,
                                                      @PathVariable("phoneNum") String phoneNum,
                                                      @RequestBody List<String> adIds) {
        // 请求参数校验
        Assert.isTrue( !Strings.isNullOrEmpty(platform), "广告渠道不能为空" );
        Assert.isTrue( !Strings.isNullOrEmpty(region), "地区不能为空" );
        Assert.isTrue( !Strings.isNullOrEmpty(phoneNum), "手机号码不能为空" );
        Assert.isTrue( !CollectionUtils.isEmpty(adIds), "广告素材id集合不能为空" );

        // 异步处理取广告素材请求
        return baseService.processRequestAsync(5000L, () -> {
            MyResponseEntity<List<AdEntity>> responseEntity = adService.getAdEntityViaFiler(platform, region, phoneNum, adIds);
            log.info("获取广告素材调用结果：{}", responseEntity);
            Integer code = responseEntity.getCode();
            String desc = responseEntity.getDesc();
            List<AdVo> adVos = adService.buildAdVo(responseEntity.getData());
            return new MyResponseEntity<>(code, desc, adVos);
        });
    }
}
