package com.echo.gulimall.order.dao;

import com.echo.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author echo
 * @email echo@gmail.com
 * @date 2021-12-30 23:06:48
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
