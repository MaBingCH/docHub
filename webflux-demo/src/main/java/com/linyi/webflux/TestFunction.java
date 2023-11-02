package com.linyi.webflux;

import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class TestFunction implements HandlerFunction<ServerResponse> {

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return ServerResponse.ok().body(
                Mono.just(parse(serverRequest, "args1") + parse(serverRequest, "args2"))
                , Integer.class);
    }

    private int parse(final ServerRequest request, final String param) {
        return Integer.parseInt(request.queryParam(param).orElse("0"));
    }
}

