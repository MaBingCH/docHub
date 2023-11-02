package com.linyi.webflux.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class RequestHandler {

    public Mono<ServerResponse> sayHello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
            .bodyValue("Hello, WebFlux!");
    }

    public Mono<ServerResponse> greet(ServerRequest request) {
        String name = request.pathVariable("name");
        String greeting = "Hello, " + name + "!";
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
            .bodyValue(greeting);
    }
}