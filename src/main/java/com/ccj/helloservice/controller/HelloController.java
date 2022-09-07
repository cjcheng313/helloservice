package com.ccj.helloservice.controller;

import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.mapper.UserMapper;
import com.ccj.helloservice.service.HelloService;
import com.ccj.helloservice.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/hello")
    public String Hello() {
        return "Hello world";
    }

    @GetMapping(value = "/hello/{id}")
    public String Hello(@PathVariable String id){
        return helloService.HelloByAlais(id);
    }
}
