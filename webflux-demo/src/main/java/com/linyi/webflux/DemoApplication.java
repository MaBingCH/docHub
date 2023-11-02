package com.linyi.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.linyi.webflux.handler.RequestHandler;

import reactor.core.publisher.Mono;

@Configuration
@EnableR2dbcRepositories
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/add"), new TestFunction());
    }
    
    @Bean
    public RouterFunction<ServerResponse> route1() {
        return RouterFunctions.route(RequestPredicates.GET("/hello"), new HandlerFunction<ServerResponse>() {
            @Override
            public Mono<ServerResponse> handle(org.springframework.web.reactive.function.server.ServerRequest request) {
                return ServerResponse.ok().body(Mono.just("Hello, RouterFunctions!"), String.class);
            }
        });
    }
    
    @Bean
    public RouterFunction<ServerResponse> route2(RequestHandler handler) {
    	return RouterFunctions.route(RequestPredicates.GET("/api2/hello"), handler::sayHello)
            .andRoute(RequestPredicates.GET("/api2/greet/{name}"), handler::greet);
    }


}
