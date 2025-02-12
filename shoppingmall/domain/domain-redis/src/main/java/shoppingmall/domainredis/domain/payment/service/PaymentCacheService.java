package shoppingmall.domainredis.domain.payment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainredis.common.annotation.DomainRedisService;
import shoppingmall.domainredis.domain.dto.PaymentCacheDto;

import java.time.Duration;

@DomainRedisService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REDIS_PAYMENT_PREFIX = "payment_order";


    @Transactional
    public void savePaymentCache(final Long tossPaymentId, final PaymentCacheDto paymentCacheDto) {

        // Write-Through : Redis에 저장
        String paymentKey = REDIS_PAYMENT_PREFIX + tossPaymentId;

        redisTemplate.opsForValue().set(paymentKey, paymentCacheDto);

        // Cache Store 내에 하루 저장
        // TODO : 결제 내역을 Cache에 얼마나 저장해야하는가?
        redisTemplate.expire(paymentKey, Duration.ofDays(1));

    }

    public PaymentCacheDto getPaymentCache(final Long tossPaymentId) {
        String paymentKey = REDIS_PAYMENT_PREFIX + tossPaymentId;

        PaymentCacheDto paymentCacheDto = (PaymentCacheDto) redisTemplate.opsForValue().get(paymentKey);
        return paymentCacheDto;

    }
}
