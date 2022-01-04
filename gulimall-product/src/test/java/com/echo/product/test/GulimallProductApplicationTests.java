package com.echo.product.test;

import com.echo.gulimall.product.entity.BrandEntity;
import com.echo.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class GulimallProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Test
    public void test1() {
        List<BrandEntity> list = brandService.list();
        System.out.println(list.size());

    }

}
