package com.ata.promo.factory;

import com.ata.promo.serializer.MsgPackRedisSerializer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisTemplateFactory {

    private LettuceConnectionFactory connFactory;

    public RedisTemplateFactory(LettuceConnectionFactory connFactory) {
        this.connFactory = connFactory;
    }

    public <V> ReactiveRedisTemplate<String, V> create(Class<V> vClass) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        RedisSerializationContext<String, V> serializationContext = RedisSerializationContext.<String, V>newSerializationContext()
                .key(keySerializer)
                .value(new MsgPackRedisSerializer<>(vClass))
                .build();
        return new ReactiveRedisTemplate<>(connFactory, serializationContext);
    }

    public <HK, HV> ReactiveRedisTemplate<String, HV> create(Class<HK> hkClass, Class<HV> hvClass) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        final MsgPackRedisSerializer<HV> hvMsgPackRedisSerializer = new MsgPackRedisSerializer<>(hvClass);
        RedisSerializationContext<String, HV> serializationContext = RedisSerializationContext.<String, HV>newSerializationContext()
                .key(keySerializer)
                .value(hvMsgPackRedisSerializer)
                .hashKey(new MsgPackRedisSerializer<>(hkClass))
                .hashValue(hvMsgPackRedisSerializer)
                .build();
        return new ReactiveRedisTemplate<>(connFactory, serializationContext);
    }
}
