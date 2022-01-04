package com.echo.gulimall.product.dao;

import com.echo.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author echo
 * @email echo@gmail.com
 * @date 2021-12-23 22:46:49
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
