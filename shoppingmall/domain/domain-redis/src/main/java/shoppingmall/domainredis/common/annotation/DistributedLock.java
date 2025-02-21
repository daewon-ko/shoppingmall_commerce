package shoppingmall.domainredis.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface DistributedLock {
    String key();   // key 이름
    long waitTime() default 3L;  // 디폴트 대기시간
    long leaseTime() default 5L; // 디폴트 잠금시간
    TimeUnit timeUnit() default TimeUnit.SECONDS; // 디폴트 시간단위
}
