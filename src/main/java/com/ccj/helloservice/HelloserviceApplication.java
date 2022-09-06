package com.ccj.helloservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.ccj.helloservice.mapper")
public class HelloserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloserviceApplication.class, args);
    }

}
