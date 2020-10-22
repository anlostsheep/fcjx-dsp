package com.richstonedt.fcjx.advertisement.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * <b><code>DistributedLocker</code></b>
 * <p/>
 * redisson - 分布式锁
 * <p/>
 * <b>Creation Time:</b> 2020/4/29 11:41.
 *
 * @author dengzhen
 * @since smartpush-cmgddr-dsp-be 0.1.0
 */
public interface DistributedLocker {

    /**
     * 锁
     *
     * @param lockKey 锁名称
     * @return 锁对象
     */
    RLock lock(String lockKey);

    /**
     * 锁-锁超时
     *
     * @param lockKey 锁名称
     * @param timeout 超时时间
     * @return 锁对象
     */
    RLock lock(String lockKey, int timeout);

    /**
     * 锁-锁超时-超时时间类型
     *
     * @param lockKey 锁名称
     * @param timeUnit    时间类型
     * @param timeout 超时时间
     * @return 锁对象
     */
    RLock lock(String lockKey, TimeUnit timeUnit, int timeout);

    /**
     * 加锁操作
     *
     * @param lockKey     锁名称
     * @param unit        事件类型
     * @param waitTime    锁时间
     * @param releaseTime 施放时间
     * @return true/false
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int releaseTime);

    /**
     * 解锁-key
     *
     * @param lockKey 锁名称
     */
    void unlock(String lockKey);

    /**
     * 解锁-锁对象
     *
     * @param lock 锁对象
     */
    void unlock(RLock lock);
}
