package com.linyi.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LogFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // TODO pre-hanlder
        long startTime = System.currentTimeMillis();
        String path = exchange.getRequest().getURI().getPath();
        log.info("Serving '{}'", path);

        return chain.filter(exchange).doAfterTerminate(() -> {
                    // TODO after-hanlder
                    exchange.getResponse().getHeaders().entrySet().forEach(e ->
                            log.info("Response header '{}': {}", e.getKey(), e.getValue()));

                            log.info("Served '{}' as {} in {} msec",
                            path,
                            exchange.getResponse().getStatusCode(),
                            System.currentTimeMillis() - startTime);
                }
        );
    }
}