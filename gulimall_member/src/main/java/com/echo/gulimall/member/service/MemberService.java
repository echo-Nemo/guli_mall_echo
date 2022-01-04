package com.echo.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echo.common.utils.PageUtils;
import com.echo.gulimall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2022-01-03 17:31:17
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

