package shoppingmall.domainredis.common.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import shoppingmall.domainredis.common.annotation.DistributedLock;
import shoppingmall.domainredis.common.util.DistributedLockKeyparser;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class DistributedLockAop {
    private final RedissonClient redissonClient;
    private final DistributedLockKeyparser lockKeyParser;


    @Around("@annotation(distributedLock)")
    public Object arroundLock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {

        String spelKey = distributedLock.key();
        String lockKey = lockKeyParser.parseKey(spelKey, joinPoint);


        RLock lock = redissonClient.getLock(lockKey);
        boolean locked = false;
        try {
            locked = lock.tryLock(
                    distributedLock.waitTime(),
                    distributedLock.leaseTime(),
                    distributedLock.timeUnit()
            );

            if (!locked) {
                throw new IllegalStateException("해당 Key에 대해서 이미 락이 점유 중입니다. 동일한 요청에 대해서는 유효할 수 없습니다. 다시 확인해주세요." + lockKey);
            }

            return joinPoint.proceed();
        } catch (InterruptedException e) {
            log.error("Locking Error", e);
            throw new IllegalStateException("Locking Error", e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                try {
                    lock.unlock();
                } catch (Exception e) {
                    log.info("Redisson Lock Already UnLock {}, {} ",
                            lockKey, e.getMessage()
                    );
                }
            }

        }


    }
}
