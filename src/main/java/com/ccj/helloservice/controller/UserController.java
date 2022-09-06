package com.ccj.helloservice.controller;

import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("user/{id}")
    public String getUserNameById(@PathVariable String id){
        return userService.getUserNameById(id);
    }

    @PostMapping("user")
    public  String createUser(User user){
        return userService.createUser(user);
    }

}
