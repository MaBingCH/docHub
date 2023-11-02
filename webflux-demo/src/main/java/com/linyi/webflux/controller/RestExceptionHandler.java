package com.linyi.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
//@Order(-2)
public class RestExceptionHandler  implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.info("==========RestExceptionHandler==========");
        if (ex instanceof PostNotFoundException) {
            log.info("==========catch PostNotFoundException==========");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);

            // marks the response as complete and forbids writing to it
            return exchange.getResponse().setComplete();
        }
        return Mono.error(ex);
    }
}

