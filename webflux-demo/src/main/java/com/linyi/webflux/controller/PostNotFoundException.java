package com.linyi.webflux.controller;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String data) {
        super("Post:" + data +" is not found.");
    }
}

