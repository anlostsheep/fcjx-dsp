package com.richstonedt.fcjx.dsp.blackwhitelist.service;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.Funnels;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.richstonedt.fcjx.dsp.blackwhitelist.constant.Constant;
import com.richstonedt.fcjx.dsp.blackwhitelist.entity.PhoneListEntity;
import com.richstonedt.fcjx.dsp.blackwhitelist.repository.PhoneListRepository;
import com.richstonedt.fcjx.dsp.common.redis.RedisBloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

/**
 * <b><code>PhoneListService</code></b>
 * <p/>
 * 号码包服务
 * <p/>
 * <b>Creation Time:</b> 2020/1/14 9:56.
 *
 * @author user
 * @since dsp_blackwhitelist
 */
//@Service
@Slf4j
public abstract class BasePhoneListService {

    private final PhoneListRepository phoneListRepository;

    /**
     * 执行线程池
     */
    private final ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("PhonePackageService thread")
            .build();
    private final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(5, 50, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(20), threadFactory);

    /**
     * 号码名单布隆过滤器
     */
    final RedisBloomFilter bloomFilter;

    BasePhoneListService(PhoneListRepository phoneListRepository, RedisTemplate<String, String> redisTemplate, Integer total, Double fpp, String key) {
        this.phoneListRepository = phoneListRepository;
        this.bloomFilter = new RedisBloomFilter(Funnels.stringFunnel(Charsets.UTF_8), total, fpp, redisTemplate, key);
    }

    protected abstract void initBloomFilter(List<PhoneListEntity> entities);

    String generateBloomFilterKey(String phoneNum, String tag, Byte status) {
        return phoneNum + "_" + tag + "_" + status;
    }

    void resetBloomFilter() {
        bloomFilter.reset();
    }

    /**
     * 判断号码是否在名单中
     * 即可能在布隆过滤器中，
     * 布隆过滤器返回false时，
     * 即一定不存在，返回真时，
     * 只代表可能存在
     * @param phoneNum
     * @param tag
     * @param status
     * @return
     */
    Boolean mightContain(String phoneNum, String tag, Byte status) {
        String key = generateBloomFilterKey(phoneNum, tag, status);
        return bloomFilter.mightContain(key);
    }

    void save(List<PhoneListEntity> entities) {
        phoneListRepository.saveAll(entities);
        // 异步初始化布隆过滤器
        executor.submit( () -> initBloomFilter(entities) );
    }

    List<PhoneListEntity> getPhoneListEntity(List<String> phoneNums) {
        return phoneListRepository.findByPhoneNumIn(phoneNums);
    }

    List<PhoneListEntity> getPhoneListEntity(String tag, PageRequest pageRequest) {
        return phoneListRepository.findByTag(tag, pageRequest);
    }

    List<PhoneListEntity> getPhoneListEntity(String tags) {
        return phoneListRepository.findByTag(tags);
    }

    /**
     * 解析号码包
     * @param url
     * @return
     */
    List<String> parsingByUrl(String url) {
        List<String> phoneNumList = Lists.newArrayList();
        try ( BufferedReader reader = new BufferedReader( new InputStreamReader( new URL(url).openStream() ) ) ) {
            String phoneNum;
            while ( (phoneNum = reader.readLine()) != null ) {
                //TODO 校验手机号码格式
                phoneNumList.add(phoneNum);
            }
        } catch (IOException e) {
            log.error("下载解析号码包错误 ======> {}", e);
        }

        return phoneNumList;
    }

    PhoneListEntity buildEntity(String tag, String url) {
        return PhoneListEntity.builder()
                .status(Constant.PhoneList.STATUS_ON)
                .tag(tag)
                .url(url)
                .createAt(new Date())
                .updateAt(new Date())
                .build();
    }

    List<PhoneListEntity> buildEntity(List<String> phoneNums, Byte status, String tag) {
        return phoneNums.stream()
                .map(num -> PhoneListEntity.builder()
                        .phoneNum(num)
                        .tag(tag)
                        .status(status)
                        .createAt(new Date())
                        .updateAt(new Date())
                        .build())
                .collect(toList());
    }
}
