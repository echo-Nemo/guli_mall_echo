package com.echo.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echo.common.utils.PageUtils;
import com.echo.gulimall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2022-01-03 16:55:42
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

