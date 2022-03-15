package com.echo.gulimall.product.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("product/redis")
public class RedisController {
    @Resource
    private RedisTemplate redisTemplate;

    String key = "testZset";

    @GetMapping("/testRedisTtl")
    public String testRedisTtl() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        String s = jedis.ping();
        return s;
    }


    @GetMapping("/getShopScore/{shopNum}")
    public String getShopScore(@PathVariable String shopNum) {
        //   addData();
        String s = addScore(shopNum);
        return s;
    }


    @GetMapping("/getAfterScore/{shopNum}")
    public String getAfterScore(@PathVariable String shopNum) {
        Double score = redisTemplate.opsForZSet().score(key, "shop".concat(shopNum));
        return score.toString();
    }


    @GetMapping("/getScores")
    public String getScores() {
        return getList();
    }


    // 使用zset向redis中添加数据
    public void addData() {

        // 设置键的过期时间
        //  redisTemplate.expire(key, 10000L, TimeUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            //    int score = new Random().nextInt(100);
            String s = "shop" + i;
            redisTemplate.opsForZSet().add(key, s, i);
        }
    }

    // 给传入的shop 分数在1
    public String addScore(String i) {
        redisTemplate.opsForZSet().incrementScore(key, "shop".concat(i), 1);
        Double score = redisTemplate.opsForZSet().score(key, "shop".concat(i));
        //redisTemplate.opsForZSet().add(key, "shop".concat(i), score);
        return score.toString();
    }

    // 查询redis中数据并根据score 进行排序
    public String getList() {
        Set set = redisTemplate.opsForZSet().rangeByScore(key, 0,20);
        return set.toString();
    }

}
