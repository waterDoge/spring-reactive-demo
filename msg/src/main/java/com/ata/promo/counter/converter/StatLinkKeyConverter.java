package com.ata.promo.counter.converter;

import com.ata.promo.RedisKeyConstants;
import com.ata.promo.counter.StatLinkCounter;

import java.time.LocalDate;
import java.util.StringJoiner;

public enum StatLinkKeyConverter implements RedisKeyConverter<StatLinkCounter> {

    CLK_INSTANCE(RedisKeyConstants.STAT_LINK_CLK_PREFIX),
    IMP_INSTANCE(RedisKeyConstants.STAT_LINK_IMP_PREFIX);

    private final String prefix;

    StatLinkKeyConverter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public StatLinkCounter toPojo(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
        final String[] split = key.split(RedisKeyConstants.DELIMITER);
        if (split.length != 6) {
            throw new IllegalArgumentException("invalid key: " + key);
        }
        StatLinkCounter msg = new StatLinkCounter();
        msg.setStatDate(LocalDate.parse(split[1]));
        msg.setChannelId(Long.valueOf(split[2]));
        msg.setSubCode(split[3]);
        msg.setProductionId(Long.valueOf(split[4]));
        msg.setLinkId(Long.valueOf(split[5]));
        return msg;
    }

    @Override
    public String toKey(StatLinkCounter msg) {
        return new StringJoiner(RedisKeyConstants.DELIMITER).add(prefix)
                .add(msg.getStatDate().toString())
                .add(msg.getChannelId().toString())
                .add(msg.getSubCode())
                .add(msg.getProductionId().toString())
                .add(msg.getLinkId().toString()).toString();
    }
}
