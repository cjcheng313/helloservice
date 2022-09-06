package com.ccj.helloservice.service.impl;

import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.mapper.UserMapper;
import com.ccj.helloservice.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String Hello(String id) {
        if(id==null || id.length()==0){
            return "Hello Wold";
        }
        String key = "User:"+id;
        Object userObj = redisTemplate.opsForValue().get(key);
        if(userObj==null){
            synchronized (this.getClass()){
                if(userObj==null){
                    User user = userMapper.selectById(id);
                    if(user==null){
                        redisTemplate.opsForValue().set(key,"Wold",48,TimeUnit.HOURS);
                        return "Hello Wold";
                    }
                    redisTemplate.opsForValue().set(key,user.getName(), 48,TimeUnit.HOURS);
                    System.out.println("----->get from db");
                    return "Hello " + user.getName() ;
                }
            }
        }
        System.out.println("---->get from redis");
        return "Hello "+ userObj;
    }
}
