package com.ata.promo.websocket;

import com.ata.promo.counter.StatChannelCounter;
import com.ata.promo.counter.converter.StatChannelKeyConverter;
import com.ata.promo.msg.ChannelAppMsg;
import com.ata.promo.service.ChannelAppService;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@SuppressWarnings("NullableProblems")
@Component
public class EchoHandler implements WebSocketHandler {

    private ChannelAppService channelAppService;
    private ReactiveRedisConnectionFactory factory;

    public EchoHandler(ChannelAppService channelAppService, ReactiveRedisConnectionFactory factory) {
        this.channelAppService = channelAppService;
        this.factory = factory;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(channelAppService::get)
                .map(channelAppMsg -> new StatChannelCounter(channelAppMsg, "", ""))
                .map(StatChannelKeyConverter.CLK_INSTANCE::toKey)
                .map(key -> ByteBuffer.wrap(key.getBytes()))
                .flatMap(key->factory.getReactiveConnection().numberCommands().incrBy(key,0))
                .checkpoint()
                .map(Object::toString)
                .switchIfEmpty(Mono.just("not found"))
                .map(session::textMessage));
    }
}
