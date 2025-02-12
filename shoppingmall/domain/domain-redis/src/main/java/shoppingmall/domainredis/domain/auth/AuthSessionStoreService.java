//package shoppingmall.domainredis.domain.auth;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import shoppingmall.domainredis.common.annotation.DomainRedisService;
//
//import java.util.concurrent.TimeUnit;
//
//@DomainRedisService
//@RequiredArgsConstructor
//public class AuthSessionStoreService {
//    // TODO : SessionStore를 Redis로 구현할 경우 Key는 String으로 하는게 가장 적절할까?
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final String REDIS_SESSION_PREFIX = "session:";
//    // 세션 저장소에 30분간 저장하도록 설정
//    private static final long SESSION_EXPIRE_TIME = 30;
//
//
//    public void saveSession(String sessionId, Object session) {
//        final String key = REDIS_SESSION_PREFIX + sessionId;
//        redisTemplate.opsForValue().set(key, session, SESSION_EXPIRE_TIME, TimeUnit.MINUTES);
//    }
//
//    public void deleteSession(String sessionId) {
//        final String key = REDIS_SESSION_PREFIX + sessionId;
//        redisTemplate.delete(key);
//    }
//
//    public Object getSession(String sessionId) {
//        final String key = REDIS_SESSION_PREFIX + sessionId;
//        return redisTemplate.opsForValue().get(key);
//    }
//}
