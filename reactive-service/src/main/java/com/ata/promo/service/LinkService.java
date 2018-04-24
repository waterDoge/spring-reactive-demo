package com.ata.promo.service;

import com.ata.promo.RedisKeyConstants;
import com.ata.promo.factory.RedisTemplateFactory;
import com.ata.promo.msg.LinkMsg;
import org.springframework.stereotype.Service;

@Service
public class LinkService extends BaseService<String, LinkMsg> {
    public LinkService(RedisTemplateFactory factory) {
        super(factory, RedisKeyConstants.LINK_HASH, String.class, LinkMsg.class);
    }
}
