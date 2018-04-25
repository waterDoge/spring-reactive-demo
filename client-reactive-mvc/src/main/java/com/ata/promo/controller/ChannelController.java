package com.ata.promo.controller;

import com.ata.promo.counter.StatChannelCounter;
import com.ata.promo.counter.converter.StatChannelKeyConverter;
import com.ata.promo.msg.ChannelAppMsg;
import com.ata.promo.service.ChannelAppService;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@RestController
@RequestMapping("channel")
public class ChannelController {

    private ChannelAppService channelAppService;
    private ReactiveRedisConnectionFactory factory;

    public ChannelController(ChannelAppService channelAppService, ReactiveRedisConnectionFactory factory) {
        this.channelAppService = channelAppService;
        this.factory = factory;
    }

    //rest api
    @GetMapping({"app", "app/{code}"})
    public Publisher<ChannelAppMsg> apps(@PathVariable(required = false) String code) {
        return StringUtils.isBlank(code) ? channelAppService.get() : channelAppService.get(code);
    }

    //server sent event
    @GetMapping("app/{code}/counter")
    public Publisher<ServerSentEvent<Integer>> counter(@PathVariable final String code) {
        final Mono<ChannelAppMsg> channelAppMsgMono = channelAppService.get(code);
        return channelAppMsgMono.map(channelAppMsg -> new StatChannelCounter(channelAppMsg, "", ""))
                .map(StatChannelKeyConverter.CLK_INSTANCE::toKey)
                .map(key -> ByteBuffer.wrap(key.getBytes()))
                .flatMap(key->factory.getReactiveConnection().numberCommands().incrBy(key,0))
                .map(i -> ServerSentEvent.builder(i).build());
    }
}
