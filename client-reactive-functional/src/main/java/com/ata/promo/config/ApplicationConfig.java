package com.ata.promo.config;

import com.ata.promo.handler.ChannelAppRedirectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ApplicationConfig {
    @SuppressWarnings("NullableProblems")
    @Bean
    public RouterFunction<ServerResponse> routerFunction(final ChannelAppRedirectHandler handler) {
        return RouterFunctions
                //RequestPredicates.path 的参数开头的/不能省略不然无法匹配
                .route(RequestPredicates.method(HttpMethod.GET).and(RequestPredicates.path("/v1/slink/{code}")),
                handler::redirect)
                .andRoute(RequestPredicates.method(HttpMethod.GET).and(RequestPredicates.path("/v1/slink")),
                handler::redirect);
    }
}
