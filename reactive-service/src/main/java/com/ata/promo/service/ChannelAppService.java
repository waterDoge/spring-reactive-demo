package com.ata.promo.service;

import com.ata.promo.RedisKeyConstants;
import com.ata.promo.factory.RedisTemplateFactory;
import com.ata.promo.msg.ChannelAppMsg;
import org.springframework.stereotype.Service;

@Service
public class ChannelAppService extends BaseService<String, ChannelAppMsg> {
    public ChannelAppService(final RedisTemplateFactory factory) {
        super(factory, RedisKeyConstants.CHANNEL_APP_HASH, String.class, ChannelAppMsg.class);
    }
}
