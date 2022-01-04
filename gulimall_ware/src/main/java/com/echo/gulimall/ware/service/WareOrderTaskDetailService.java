package com.echo.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.echo.common.utils.PageUtils;
import com.echo.gulimall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2022-01-03 16:55:41
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

