package com.ata.promo.counter.converter;

import com.ata.promo.RedisKeyConstants;
import com.ata.promo.counter.StatChannelCounter;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum StatChannelKeyConverter implements RedisKeyConverter<StatChannelCounter> {

    CLK_INSTANCE(RedisKeyConstants.STAT_CHANNEL_CLK_PREFIX),
    IMP_INSTANCE(RedisKeyConstants.STAT_CHANNEL_IMP_PREFIX);

    private final String prefix;

    StatChannelKeyConverter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public StatChannelCounter toPojo(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        final String[] split = key.split(RedisKeyConstants.DELIMITER);
        if (split.length < 4) {
            throw new IllegalArgumentException("invalid key: " + key);
        }
        StatChannelCounter msg = new StatChannelCounter();
        msg.setStatDate(LocalDate.parse(split[1]));
        msg.setChannelId(Long.valueOf(split[2]));
        msg.setSubCode(split[3]);
        if (split.length >= 5) {
            msg.setExt1(split[4]);
        }
        if (split.length == 6) {
            msg.setExt2(split[5]);
        }
        return msg;
    }

    @Override
    public String toKey(StatChannelCounter msg) {
        return Stream.of(prefix,
                msg.getStatDate(),
                msg.getChannelId(),
                msg.getSubCode(),
                msg.getExt1(),
                msg.getExt2()).map(Object::toString).collect(Collectors.joining(RedisKeyConstants.DELIMITER));
    }
}
