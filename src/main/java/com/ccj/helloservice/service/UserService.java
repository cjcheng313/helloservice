package com.ccj.helloservice.service;

import com.ccj.helloservice.entity.User;

import java.util.Set;

public interface UserService {
    String getUserNameById(String id);
    String createUser(User user);
    Set<String> getUserAliasById(String id);
}
