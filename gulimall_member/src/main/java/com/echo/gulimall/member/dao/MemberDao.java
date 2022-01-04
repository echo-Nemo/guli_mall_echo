package com.echo.gulimall.member.dao;

import com.echo.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author echo
 * @email echo@gmail.com
 * @date 2022-01-03 17:31:17
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
