package com.echo.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echo.common.utils.PageUtils;
import com.echo.gulimall.order.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2021-12-30 23:06:48
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

