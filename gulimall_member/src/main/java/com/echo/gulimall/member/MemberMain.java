package com.echo.gulimall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.echo.gulimall.member.dao")
@SpringBootApplication
@EnableDiscoveryClient
public class MemberMain {
    public static void main(String[] args) {
        SpringApplication.run(MemberMain.class,args);
    }
}
