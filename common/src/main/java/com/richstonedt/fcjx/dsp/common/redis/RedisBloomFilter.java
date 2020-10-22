package com.richstonedt.fcjx.dsp.common.redis;

import com.google.common.base.Strings;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <b><code>PhoneListBloomFilter</code></b>
 * <p/>
 * 基于Redis的布隆过滤器
 * 每个客户端都有一个本地布隆过滤器
 * 当某个客户端更新了本地布隆过滤，就更新Redis上保存的版本号，以及同步其本地布隆过滤器到Redis
 * 当某个客户端验证是否包含时，要先读取版本号，如果版本已改变，就从Redis上下载最新布隆过滤器同步到本地
 * <p/>
 * <b>Creation Time:</b> 2020/2/19 13:41.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@Slf4j
public class RedisBloomFilter {

    private final RedisLock redisLock;;
    private volatile BloomFilter<String> bloomFilter;
    private final RedisTemplate<String, String> redisTemplate;
    private final String BASE_KEY;
    private final String BLOOM_FILTER_VERSION_KEY;
    private final AtomicLong version = new AtomicLong(0L);
    private final Funnel<CharSequence> funnel;
    private final int expectedInsertions;
    private final double fpp;

    public RedisBloomFilter(Funnel<CharSequence> funnel, int expectedInsertions, double fpp, RedisTemplate<String, String> redisTemplate, String redisKey) {
        this.funnel = funnel;
        this.expectedInsertions = expectedInsertions;
        this.fpp = fpp;
        this.bloomFilter = BloomFilter.create(funnel, expectedInsertions, fpp);
        this.redisTemplate = redisTemplate;
        this.BASE_KEY = redisKey;
        this.BLOOM_FILTER_VERSION_KEY = this.BASE_KEY + "_BLOOM_FILTER_VERSION_KEY";
        this.redisLock = new RedisLock(redisTemplate);
    }

    public BloomFilter<String> getBloomFilter() {
        return bloomFilter;
    }

    public long approximateElementCount() {
        return bloomFilter.approximateElementCount();
    }

    /**
     * 置位布隆过滤器
     * @param key
     */
    public Boolean put(String key) {
        readFrom();
        boolean flag = bloomFilter.put(key);
        writeTo();
        return flag;
    }

    /**
     * 批量置位布隆过滤器
     * @param keys
     * @return
     */
    public void put(List<String> keys) {
        String key = "RedisBloomFilter";
        String value = UUID.randomUUID().toString();
        try {
            while (true) {
                Boolean lock = redisLock.tryLock(key, value, 10 * 60 * 1000L);
                if (lock) {
                    readFrom();
                    keys.forEach(bloomFilter::put);
                    writeTo();
                    log.info("{}开始操作布隆过滤器...", Thread.currentThread());
                    break;
                }
                log.info("已经有一个节点开始操作布隆过滤器了");
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (Exception e) {
            log.error("操作布隆过滤器出错：{}", e.getMessage());
        } finally {
            redisLock.unlock(key, value);
        }
    }

    /**
     * 可能包含判断
     * @param key
     * @return
     */
    public Boolean mightContain(String key) {
        readFrom();
        return bloomFilter.mightContain(key);
    }

    /**
     * 同步本地布隆过滤器到Redis
     */
    private void writeTo() {
        try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream(10240) ) {
            bloomFilter.writeTo(outputStream);
            redisTemplate.execute((RedisCallback<Object>) rc -> {
                String val = encode(outputStream.toByteArray());
                rc.set(BASE_KEY.getBytes(), val.getBytes());
                Long incr = rc.incr(BLOOM_FILTER_VERSION_KEY.getBytes());
                Optional.ofNullable(incr).ifPresent(version::set);
                log.info("同步本地布隆过滤器到Redis");
                return null;
            });
        } catch (IOException e) {
            log.error("写入Redis布隆过滤器发生了IO异常: {}", e.getMessage());
        } catch (Exception e) {
            log.error("写入Redis布隆过滤器发生了异常: {}", e.getMessage());
        }
    }

    /**
     * 从Redis上同步布隆过滤器
     */
    private void readFrom() {
        try {
            long vs = getVersion();

            // 本地版本号与远程版本号相同，不需要同步布隆过滤器
            if ( version.compareAndSet(vs, vs) ) {
                return;
            }

            // 本地版本与远程版本号不同，同步版本号以及布隆过滤器
            String bf = redisTemplate.opsForValue().get(BASE_KEY);
            if ( !Strings.isNullOrEmpty(bf) ) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream( decode(bf) );
                bloomFilter = BloomFilter.readFrom(inputStream, funnel);
                log.info("从Redis上同步布隆过滤器");
            }
            version.set(vs);
        } catch (IOException e) {
            log.error("读取Redis布隆过滤器发生了IO异常: {}", e.getMessage());
        } catch (Exception e) {
            log.error("读取Redis布隆过滤器发生了异常: {}", e.getMessage());
        }
    }

    /**
     * 重置布隆过滤器
     */
    public void reset() {
        bloomFilter = BloomFilter.create(funnel, expectedInsertions, fpp);
        writeTo();
    }

    /**
     * 读取远程版本号
     * @return
     */
    private long getVersion() {
        String s = redisTemplate.opsForValue().get(BLOOM_FILTER_VERSION_KEY);
        return Strings.isNullOrEmpty(s) ? 0L : Longs.tryParse(s);
    }

    private String encode(byte[] bytes) {
        Base64 base64 = new Base64();
        return base64.encodeToString(bytes);
    }

    private byte[] decode(String s) {
        Base64 base64 = new Base64();
        return base64.decode(s);
    }
}
