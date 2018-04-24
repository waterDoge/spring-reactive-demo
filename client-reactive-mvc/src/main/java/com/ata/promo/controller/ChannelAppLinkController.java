package com.ata.promo.controller;

import com.ata.promo.Constants;
import com.ata.promo.counter.StatChannelCounter;
import com.ata.promo.counter.StatLinkCounter;
import com.ata.promo.counter.converter.StatChannelKeyConverter;
import com.ata.promo.counter.converter.StatLinkKeyConverter;
import com.ata.promo.msg.ChannelAppMsg;
import com.ata.promo.msg.LinkMsg;
import com.ata.promo.service.ChannelAppService;
import com.ata.promo.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.time.Duration;

@Controller
@RequestMapping("v1/slink")
public class ChannelAppLinkController {
    private final Logger logger = LoggerFactory.getLogger(ChannelAppLinkController.class);
    private ChannelAppService channelAppService;
    private LinkService linkService;
    private ReactiveRedisConnectionFactory factory;
    private StatLinkKeyConverter statLinkClkKeyConverter = StatLinkKeyConverter.CLK_INSTANCE;
    private StatChannelKeyConverter statChannelKeyConverter = StatChannelKeyConverter.CLK_INSTANCE;

    public ChannelAppLinkController(ChannelAppService channelAppService, LinkService linkService, LettuceConnectionFactory factory) {
        this.channelAppService = channelAppService;
        this.linkService = linkService;
        this.factory = factory;
    }

    @GetMapping("{code}")
    public Mono<String> redirect(@PathVariable String code,
                               @RequestParam(required = false, defaultValue = "") String extKey1,
                               @RequestParam(required = false, defaultValue = "") String extKey2) {

        final Mono<ChannelAppMsg> channelAppMsg = channelAppService.get(code);

        return channelAppMsg
                .map(msg -> {
                    String link = msg.getLink();

                    if (link != null) {
                        increaseChannelCounter(msg, extKey1, extKey2);
                        increaseLinkCounter(msg, link);
                        link = replaceHolders(extKey1, extKey2, msg, link);
                    }
                    return link;
                })
                .map(UrlBasedViewResolver.REDIRECT_URL_PREFIX::concat)
                .defaultIfEmpty("/404.html")
                .timeout(Duration.ofSeconds(1L));
        //reactive web没有支持forward,因此无法forward到404.html静态页面,此处匹配的是templates下的404.html（模板文件）
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
