package shoppingmall.domainredis.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    private static final String REDISSON_HOST_PREFIX = "redis://";


    @Bean
    public RedissonClient redissonClient() {

        RedissonClient redissonClient = null;
        Config config = new Config();
        config.useSingleServer()
                .setPassword(redisPassword)
                .setAddress(REDISSON_HOST_PREFIX + redisHost + ":" + redisPort);
        redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
