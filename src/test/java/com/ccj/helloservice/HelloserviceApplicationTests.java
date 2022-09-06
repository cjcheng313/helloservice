package com.ccj.helloservice;

import com.ccj.helloservice.entity.User;
import com.ccj.helloservice.mapper.UserMapper;
import com.ccj.helloservice.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HelloserviceApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;
    @Autowired
    HelloService helloService;


    public void TestHello() throws InterruptedException{
        String id = "123";
        ExecutorService es = Executors.newFixedThreadPool(200);
        for(int i = 0; i < 500; i++){
            es.submit(new Runnable() {
                @Override
                public void run() {
                    helloService.Hello(id);
                }
            });
        }
    }


    public void TestUser() throws InterruptedException{
        String id = "123";
        User user = userMapper.selectById(id);
        if(user==null){
            System.out.println("Hello Wold");
        }
        System.out.println("Hello " + user.getName());
    }

    @Test
    public void TestRedis() throws InterruptedException {
        redisTemplate.opsForValue().set("u1", "ccj");
        System.out.println(redisTemplate.opsForValue().get("u1"));
        System.out.println("-----------------");
        Set<String> mySet = new HashSet<>();
        mySet.add("n1");
        mySet.add("n2");
        mySet.add("n3");
        redisTemplate.opsForSet().add("set2",mySet.toArray());
        System.out.println(redisTemplate.hasKey("set2"));
        System.out.println(redisTemplate.opsForSet().randomMember("set2"));
        System.out.println("-----------------");

    }
}
