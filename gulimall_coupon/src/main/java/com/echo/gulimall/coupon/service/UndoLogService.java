package com.echo.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echo.common.utils.PageUtils;
import com.echo.gulimall.coupon.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2022-01-03 17:16:20
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

