package com.ccj.helloservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.entity.UserAlias;
import com.ccj.helloservice.mapper.UserAliasMapper;
import com.ccj.helloservice.mapper.UserMapper;
import com.ccj.helloservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAliasMapper userAliasMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String getUserNameById(String id) {
        System.out.println(id);
        User user = userMapper.selectById(id);
        return user.getName();
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

    @Override
    public Set<String> getUserAliasById(String id) {
        QueryWrapper<UserAlias> queryWrapper = new QueryWrapper<UserAlias>()
                .select("namealias").eq("userid",id);
        List<UserAlias> userAliasList = userAliasMapper.selectList(queryWrapper);
        Set<String> aliasSet = new HashSet();
        for(UserAlias alias : userAliasList){
            aliasSet.add(alias.getNamealias());
        }
        return aliasSet;
    }
}
