package com.ata.promo.serializer;

import org.msgpack.MessagePack;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class MsgPackRedisSerializer<T> implements RedisSerializer<T> {
    private static ThreadLocal<MessagePack> threadLocal = ThreadLocal.withInitial(MessagePack::new);

    private Class<T> clazz;

    public MsgPackRedisSerializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        final MessagePack messagePack = threadLocal.get();
        try {
            return messagePack.write(t);
        } catch (IOException e) {
            throw new SerializationException("MessagePack写异常", e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        final MessagePack messagePack = threadLocal.get();
        try {
            return messagePack.read(bytes, clazz);
        } catch (IOException e) {
            throw new SerializationException("MessagePack读异常", e);
        }
    }
}
