package com.richstonedt.fcjx.dsp.blackwhitelist.schedule;

import com.richstonedt.fcjx.dsp.blackwhitelist.service.AdService;
import com.richstonedt.fcjx.dsp.common.redis.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <b><code>ScheduledService</code></b>
 * <p/>
 * 布隆过滤器定期置位任务(减少误判率)
 * <p/>
 * <b>Creation Time:</b> 2020/2/22 17:14.
 *
 * @author liangqinglong
 * @since dsp_blackwhitelist
 */
@Slf4j
@Component
public class ScheduledService {

    private final RedisLock redisLock;
    private final AdService adService;

    public ScheduledService(RedisLock redisLock, AdService adService) {
        this.redisLock = redisLock;
        this.adService = adService;
    }

    /**
     * 每天4点重置一次布隆过滤器，降低误判率
     */
    @Scheduled(cron = "0 0 4 * * ?")
//    @Scheduled(cron = "0 */5 * * * *")
    public void resetRedisBloomFilterFromPhoneListService() {
        String key = "resetRedisBloomFilterFromPhoneListService";
        String value = UUID.randomUUID().toString();
        try {
            Boolean lock = redisLock.tryLock(key, value, 10 * 60 * 1000L);
            if (!lock) {
                log.info("已经有一个节点开始重置广告素材黑白名单的布隆过滤器了");
                return;
            }
            log.info("开始重置广告素材黑白名单的布隆过滤器...");
            adService.up();
        } catch (Exception e) {
            log.error("重置广告素材黑白名单的布隆过滤器出错：{}", e);
        } finally {
            redisLock.unlock(key, value);
        }
    }
}
