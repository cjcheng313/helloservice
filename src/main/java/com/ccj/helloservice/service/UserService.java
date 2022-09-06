package com.ccj.helloservice.service;

import com.ccj.helloservice.entity.User;

public interface UserService {
    String getUserNameById(String id);
    String createUser(User user);
}
