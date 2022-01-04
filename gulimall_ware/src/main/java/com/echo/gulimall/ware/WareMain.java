package com.echo.gulimall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.echo.gulimall.ware.dao")
@EnableDiscoveryClient
public class WareMain {
    public static void main(String[] args) {
        SpringApplication.run(WareMain.class, args);
    }
}
