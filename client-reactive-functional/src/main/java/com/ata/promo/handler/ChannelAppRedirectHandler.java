package com.ata.promo.handler;

import com.ata.promo.Constants;
import com.ata.promo.counter.StatChannelCounter;
import com.ata.promo.counter.StatLinkCounter;
import com.ata.promo.counter.converter.StatChannelKeyConverter;
import com.ata.promo.counter.converter.StatLinkKeyConverter;
import com.ata.promo.msg.ChannelAppMsg;
import com.ata.promo.msg.LinkMsg;
import com.ata.promo.service.ChannelAppService;
import com.ata.promo.service.LinkService;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.time.Duration;

@Component
public class ChannelAppRedirectHandler{

    private ChannelAppService channelAppService;
    private LinkService linkService;
    private ReactiveRedisConnectionFactory factory;
    private StatLinkKeyConverter statLinkClkKeyConverter = StatLinkKeyConverter.CLK_INSTANCE;
    private StatChannelKeyConverter statChannelKeyConverter = StatChannelKeyConverter.CLK_INSTANCE;


    public ChannelAppRedirectHandler(ChannelAppService channelAppService, LinkService linkService, LettuceConnectionFactory factory) {
        this.channelAppService = channelAppService;
        this.linkService = linkService;
        this.factory = factory;
    }

    public Mono<ServerResponse> redirect(ServerRequest request) {
        String code = request.pathVariable("code");
        String ext1 = request.queryParam("ext1").orElse("");
        String ext2 = request.queryParam("ext2").orElse("");
        final Mono<ChannelAppMsg> channelAppMsg = channelAppService.get(code);
        return channelAppMsg
                .map(msg -> {
                    String link = msg.getLink();

                    if (link != null) {
                        increaseChannelCounter(msg, ext1, ext2);
                        increaseLinkCounter(msg, link);
                        link = replaceHolders(ext1, ext2, msg, link);
                    }
                    return link;
                })
                .flatMap(s -> ServerResponse.status(HttpStatus.MOVED_PERMANENTLY).header("Location",s).build())
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e->ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }


    private String replaceHolders(@RequestParam(required = false, defaultValue = "") String extKey1, @RequestParam(required = false, defaultValue = "") String extKey2, ChannelAppMsg msg, String link) {
        if (link.contains("{")) {
            try {
                link = link.replace(Constants.URL_PLACEHOLDER_CODE, msg.getCode())
                        .replace(Constants.URL_PLACEHOLDER_EXT_KEY1, URLEncoder.encode(extKey1, "UTF-8"))
                        .replace(Constants.URL_PLACEHOLDER_EXT_KEY2, URLEncoder.encode(extKey2, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("竟然不支持UTF-8???", e);
            }
        }
        return link;
    }

    private void increaseChannelCounter(ChannelAppMsg msg, String ext1, String ext2) {
        StatChannelCounter counter = new StatChannelCounter(msg, ext1, ext2);
        final String key = statChannelKeyConverter.toKey(counter);
        increaseAndSetExpire(key);
    }

    private void increaseLinkCounter(ChannelAppMsg appMsg, String link) {
        final Mono<LinkMsg> linkMsg = linkService.get(link);

        linkMsg.map(linkMsg1 -> {
            StatLinkCounter counter = StatLinkCounter.of(appMsg, linkMsg1);
            final String key = statLinkClkKeyConverter.toKey(counter);
            return increaseAndSetExpire(key);
        }).subscribe();
    }

    private Disposable increaseAndSetExpire(String key) {
        final ByteBuffer buffer = ByteBuffer.wrap(key.getBytes());
        final ReactiveRedisConnection connection = factory.getReactiveConnection();
        connection.keyCommands().expire(buffer, Duration.ofDays(1)).subscribe();
        return connection.numberCommands().incr(buffer).subscribe();
    }
}
