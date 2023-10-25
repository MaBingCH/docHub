package com.linyi.sample.control;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserInfoControl {

    @GetMapping("/test3")
    public String test() {
        return "hello3";
    }

    @GetMapping("/test")
    public String test1() {
        return "hello1";
    }

    @GetMapping("/")
    public String getAllUsers() {
        return "xxxxx";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id) {
        return "aaaaa";
    }



}