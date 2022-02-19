package com.echo.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echo.common.utils.PageUtils;
import com.echo.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2021-12-23 22:46:49
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);


    public List<CategoryEntity> listWithTree();

    public void batchRemoveByIds(List<Long> asList);
}

