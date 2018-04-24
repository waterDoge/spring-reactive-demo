package com.ata.promo;

import com.ata.promo.handler.ChannelAppRedirectHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class ReactiveFunctionalClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveFunctionalClientApplication.class, args);
    }
}
