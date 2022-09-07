package com.ccj.helloservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.entity.UserAlias;
import com.ccj.helloservice.mapper.UserAliasMapper;
import com.ccj.helloservice.mapper.UserMapper;
import com.ccj.helloservice.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class HelloServiceImpl implements HelloService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private UserAliasMapper userAliasMapper;
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

    @Override
    public String HelloByAlais(String id) {
        if(id==null || id.length()==0){
            return "Hello Wold";
        }
        String key = "UserAlais:"+id;
        Object aliasObj = null;
        if(redisTemplate.hasKey(key)){
            aliasObj = redisTemplate.opsForSet().randomMember(key);
        }
        if(aliasObj==null){
            synchronized (this.getClass()){
                if(aliasObj==null){
                    QueryWrapper<UserAlias> queryWrapper = new QueryWrapper<UserAlias>()
                            .select("namealias").eq("userid",id);
                    List<UserAlias> userAliasList = userAliasMapper.selectList(queryWrapper);
                    User user = null;
                    if(userAliasList.size()==0){
                        user = userMapper.selectById(id);
                        if(user==null){
                            redisTemplate.opsForValue().set("User:"+id,"Wold",48,TimeUnit.HOURS);
                            return "Hello Wold";
                        }
                    }
                    Set<String> aliasSet = new HashSet<>();
                    if(userAliasList.size()==0){
                        aliasSet.add(user.getName());
                    }else{
                        for(UserAlias alias : userAliasList){
                            aliasSet.add(alias.getNamealias());
                        }
                    }

                    redisTemplate.opsForSet().add(key,aliasSet.toArray());
                    aliasObj = redisTemplate.opsForSet().randomMember(key);
                }
            }
        }
        return "Hello "+ aliasObj;
    }
}
