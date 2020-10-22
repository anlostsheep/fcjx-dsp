package com.richstonedt.fcjx.advertisement.lock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <b><code>DistributeLockerImpl</code></b>
 * <p/>
 * redisson 工具类
 * <p/>
 * <b>Creation Time:</b> 2020/4/29 11:49.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
@Slf4j
@Component
public class DistributedLockerImpl implements DistributedLocker {

    private RedissonClient redissonClient;

    @Autowired
    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit timeUnit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, timeUnit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit timeUnit, int waitTime, int releaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, releaseTime, timeUnit);
        } catch (InterruptedException e) {
            log.error("redisson 分布式锁上锁失败,异常情况:", e);
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }
}
