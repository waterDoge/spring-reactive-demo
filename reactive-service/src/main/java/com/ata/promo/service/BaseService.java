package com.ata.promo.service;

import com.ata.promo.factory.RedisTemplateFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public abstract class BaseService<HK, HV> {
    private final String KEY;
    private ReactiveHashOperations<String, HK, HV> hashOperations;

    BaseService(final RedisTemplateFactory factory, String redisKey, Class<HK> hkClass, Class<HV> hvClass) {
        KEY = redisKey;
        final ReactiveRedisTemplate<String, HV> template = factory.create(hkClass, hvClass);
        hashOperations = template.opsForHash();
    }

    public Mono<HV> get(final HK hashKey) {
        return hashOperations.get(KEY, hashKey);
    }

    public Flux<HV> get() {
        return hashOperations.values(KEY);
    }
}
