package com.richstonedt.fcjx.dsp.blackwhitelist.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.MyResponseCode;
import com.richstonedt.fcjx.dsp.blackwhitelist.domain.GlobalBlackListPackage;
import com.richstonedt.fcjx.dsp.blackwhitelist.domain.PhonePackage;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.MyResponseEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.PhoneListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.exception.ApiException;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.AdBlackWhiteListService;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.BaseService;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.GlobalBlackListService;
import com.richstonedt.fcjx.dsp.blackwhitelist.service.PhonePackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <b><code>BlackWhiteListController</code></b>
 * <p/>
 * 黑白名单API
 * <p/>
 * <b>Creation Time:</b> 2020/1/13 14:11.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@RestController
@RequestMapping("phone-package")
@Slf4j
public class BlackWhiteListController {

    @Autowired
    private GlobalBlackListService globalBlackListService;
    @Autowired
    private PhonePackageService phonePackageService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private AdBlackWhiteListService adBlackWhiteListService;

    /**
     * 保存广告素材黑白名单接口
     * @param phonePackage
     * @return
     */
    @PostMapping(value = "/ad-black-white-list")
    public DeferredResult<MyResponseEntity> save(@RequestBody PhonePackage phonePackage) {
        String[] region = phonePackage.getRegion();
        Assert.isTrue( !Strings.isNullOrEmpty(phonePackage.getAdvId()), "广告素材id不能为空" );
        Assert.isTrue( !Strings.isNullOrEmpty(phonePackage.getOpType()), "操作类型不能为空" );
        Assert.isTrue( !Strings.isNullOrEmpty(phonePackage.getPlatform()), "广告渠道不能为空" );
        Assert.isTrue( region != null && region.length > 0, "广告素材至少要属于一个地区" );

        // 异步处理黑白名单保存请求
        return baseService.processRequestAsync(5000L, () -> {
            phonePackageService.save(phonePackage, adBlackWhiteListService.getBasePhoneListService());
            return new MyResponseEntity<>().success(Lists.newArrayList());
        });
    }

    /**
     *  删除全局黑名单接口
     * @param phonePackage
     * @return
     */
    @DeleteMapping(value = "/all-black-list")
    public DeferredResult<MyResponseEntity> delete(@RequestBody GlobalBlackListPackage phonePackage) {
        List<String> phoneNums = Optional.of(phonePackage)
                .map(GlobalBlackListPackage::getPhoneNums)
                // 不处理空号码集
                .filter(nums -> !CollectionUtils.isEmpty(nums))
                .orElseThrow(() -> new ApiException(MyResponseCode.INTERNAL_SERVER_ERROR.getCode(), "全局黑名单号码包不能为空"));

        // 异步处理黑白名单删除请求
        return baseService.processRequestAsync(5000L, () -> {
            globalBlackListService.save(phoneNums, Constant.PhoneList.STATUS_OFF);
            return new MyResponseEntity<>().success(Lists.newArrayList());
        });
    }

    /**
     *  刷新全局黑名单缓存
     * @return
     */
    @PostMapping(value = "/refresh/cache/all-black-list")
    public MyResponseEntity refreshGlobalBlackListCache() {
        globalBlackListService.refreshCache();
        return new MyResponseEntity<>().success(Lists.newArrayList());
    }

    /**
     *  获取全局黑名单实体
     * @return
     */
    @GetMapping(value = "/all-black-list/{page}/{size}")
    public MyResponseEntity getGlobalBlackList(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        // 请求参数校验
        Assert.isTrue( !Objects.isNull(page) && page > 0, "当前页" );
        Assert.isTrue( !Objects.isNull(size) && size > 0, "页数" );

        List<PhoneListEntity> entities = globalBlackListService.getPhoneListEntity(PageRequest.of(page - 1, size));
        return new MyResponseEntity<>().success(entities);
    }

    /**
     *  判断号码是在全局黑名单中
     * @return
     */
    @GetMapping(value = "/all-black-list/{phoneNum}")
    public MyResponseEntity isGlobalBlackNum(@PathVariable("phoneNum") String phoneNum) {
        // 请求参数校验
        Assert.isTrue( !Strings.isNullOrEmpty(phoneNum), "号码不能为空" );
        Boolean contain = globalBlackListService.contain(phoneNum, Constant.PhoneList.STATUS_ON);
        return new MyResponseEntity<>().success(contain);
    }

    /**
     * 保存全局黑名单接口
     * @param phonePackage
     * @return
     */
    @PostMapping(value = "/all-black-list")
    public DeferredResult<MyResponseEntity> save(@RequestBody GlobalBlackListPackage phonePackage) {
        List<String> phoneNums = Optional.of(phonePackage)
                .map(GlobalBlackListPackage::getPhoneNums)
                // 不处理空号码集
                .filter(nums -> !CollectionUtils.isEmpty(nums))
                .orElseThrow(() -> new ApiException(MyResponseCode.INTERNAL_SERVER_ERROR.getCode(), "全局黑名单号码包不能为空"));

        // 异步处理黑白名单保存请求
        return baseService.processRequestAsync(5000L, () -> {
            globalBlackListService.save(phoneNums, Constant.PhoneList.STATUS_ON);
            return new MyResponseEntity<>().success(Lists.newArrayList());
        });
    }
}
