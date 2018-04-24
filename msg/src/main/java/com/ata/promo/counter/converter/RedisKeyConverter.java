package com.ata.promo.counter.converter;

public interface RedisKeyConverter<T> {
    T toPojo(String key);
    String toKey(T t);
}
