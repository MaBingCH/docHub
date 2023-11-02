package com.linyi.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class HelloController {

	 @GetMapping("/hello1")
	 public String hello1() {
		 return "hello1";
	 }
	 
	@RequestMapping("/hello")
	public Mono<String> hello() {
		return Mono.just("hello world");
	}
	
	@GetMapping(value = "/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> flux() {
	    Flux<String> flux = Flux.fromArray(new String[]{"javaboy","itboyhub","www.javaboy.org","itboyhub.com"}).map(s -> {
	        try {
	            Thread.sleep(2000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        return "my->data->" + s;
	    });

	    return flux;
	}

    public static class RestExceptionHandler {
    }
}
