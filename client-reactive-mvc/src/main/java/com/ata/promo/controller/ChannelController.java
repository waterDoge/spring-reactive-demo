package com.ata.promo.controller;

import com.ata.promo.msg.ChannelAppMsg;
import com.ata.promo.service.ChannelAppService;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("channel")
public class ChannelController {

    private ChannelAppService channelAppService;

    public ChannelController(ChannelAppService channelAppService) {
        this.channelAppService = channelAppService;
    }

    @GetMapping({"app", "app/{code}"})
    public Publisher<ChannelAppMsg> apps(@PathVariable(required = false) String code) {
        return StringUtils.isBlank(code) ? channelAppService.get() : channelAppService.get(code);
    }
}
