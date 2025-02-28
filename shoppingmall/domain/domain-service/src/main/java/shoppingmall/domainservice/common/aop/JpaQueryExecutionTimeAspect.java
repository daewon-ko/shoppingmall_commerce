package shoppingmall.domainservice.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import shoppingmall.domainservice.common.logging.dto.JpaQueryExecutionTimeDto;

@Aspect
@Component
@Slf4j
public class JpaQueryExecutionTimeAspect {

    private static final Long SLOW_QUERY_TIME = 100L; // 임의값. 추후 변동 가능

@Around("bean(*Repository)")
    public Object aroundQueryExecution(ProceedingJoinPoint jointPoint)  {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = jointPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;


        if (executionTime>= SLOW_QUERY_TIME) {
            log.info("\n"+"Slow Query Detected: {}{}", executionTime+"ms\n" , JpaQueryExecutionTimeDto.builder()
                    .query(jointPoint.getSignature().toString())
                    .executionTime(executionTime)
                    .build());
        }
        return result;
    }

}
