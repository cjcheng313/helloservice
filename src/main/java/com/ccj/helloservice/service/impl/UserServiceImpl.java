package com.ccj.helloservice.service.impl;

import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.mapper.UserMapper;
import com.ccj.helloservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String getUserNameById(String id) {
        System.out.println(id);
        List<User> userList = userMapper.selectList(null);
        return "getUserById";
    }

    @Override
    public String createUser(User user) {
        String key = "User:"+user.getId();
        redisTemplate.delete(key);
        int i = userMapper.insert(user);
        if(i>0){
            redisTemplate.opsForValue().set(key,user.getName(),48, TimeUnit.HOURS);
        }
        return i>0?"success":"failed";
    }
}
