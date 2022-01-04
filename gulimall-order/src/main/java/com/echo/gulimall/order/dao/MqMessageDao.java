package com.echo.gulimall.order.dao;

import com.echo.gulimall.order.entity.MqMessageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author echo
 * @email echo@gmail.com
 * @date 2021-12-30 23:06:48
 */
@Mapper
public interface MqMessageDao extends BaseMapper<MqMessageEntity> {
	
}
