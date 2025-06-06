package website.project.website.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 * @author mayang
 */
@Slf4j
@Component
public class LockUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final Long DEFAULT_TIMEOUT_SECONDS = 5 * 60L;

    private final TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;


    //普通加锁, key-key value-value unLockTime-锁超时时间 timeUnit-超时时间单位
    //默认超时时间5分钟

    public Boolean lock(String key, String value){
        return lock(key, value, DEFAULT_TIMEOUT_SECONDS, DEFAULT_TIMEUNIT);
    }

    public boolean lock(String key, String value, Long unLockTime, TimeUnit timeUnit){
        //尝试获取锁
        log.info("key:{}, 开始获取锁.....", key);
        Boolean setnx =  stringRedisTemplate.opsForValue().setIfAbsent(key, value,unLockTime,timeUnit);
        log.info("key:{}, 获取锁{}", key, setnx);
        if (Boolean.TRUE.equals(setnx)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    //带重试机制的加锁 key-key value-value waitTime-等待时间 reLockTime-重试时间间隔 waitTimeUnit-重试时间间隔单位 unLockTime-锁超时时间 timeUnit-超时时间单位
    //默认超时时间5分钟

    public boolean lock(String key, String value, Long waitTime, Long reLockTime, TimeUnit waitTimeUnit){
        return lock(key, value, waitTime, reLockTime, waitTimeUnit, DEFAULT_TIMEOUT_SECONDS, DEFAULT_TIMEUNIT);
    }

    public boolean lock(String key, String value, Long waitTime, Long reLockTime, TimeUnit waitTimeUnit, Long unLockTime, TimeUnit timeUnit){
        log.info("key:{}, 开始获取锁.....", key);
        //根据系统时间获取当前毫秒数,计算结束时间
        long startMillis = System.currentTimeMillis();
        long endTimeMillis = waitTimeUnit.toMillis(waitTime) + startMillis;
        //尝试获取锁
        try {
            int count = 1;
            while (System.currentTimeMillis() < endTimeMillis) {
                Boolean setnx = stringRedisTemplate.opsForValue().setIfAbsent(key, value, unLockTime, timeUnit);
                log.info("key:{}, 第{}次尝试获取锁,结果{}",key, count, setnx);
                if (Boolean.TRUE.equals(setnx)) {
                    return Boolean.TRUE;
                }
                //获取锁失败则休眠后重试，Thread.sleep()只能精确到毫秒，此处都可
                TimeUnit.MILLISECONDS.sleep(waitTimeUnit.toMillis(reLockTime));
                count++;
            }
        }catch (Exception e){
            log.info("获取锁失败,失败原因{}",e.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }

    /**
     * 解锁
     * 判断value 防止删除其余线程的锁
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key,String value){
        log.info("开始解锁");
        String lockValue = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(lockValue) || value.equals(lockValue)){
            stringRedisTemplate.delete(key);
        }
        return Boolean.TRUE;
    }
}
