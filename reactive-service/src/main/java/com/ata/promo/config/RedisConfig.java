package com.ata.promo.config;

import com.ata.promo.factory.RedisTemplateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplateFactory redisTemplateFactory(LettuceConnectionFactory connectionFactory) {
        return new RedisTemplateFactory(connectionFactory);
    }
}
