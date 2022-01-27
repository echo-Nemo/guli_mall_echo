package com.echo.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.echo.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.echo.gulimall.product.entity.CategoryEntity;
import com.echo.gulimall.product.service.CategoryService;
import com.echo.common.utils.PageUtils;
import sun.rmi.runtime.Log;


/**
 * 商品三级分类
 *
 * @author echo
 * @email echo@gmail.com
 * @date 2021-12-23 22:46:49
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 获取3级分类的列表
     */

    @RequestMapping("/list/tree")
    public R listTree() {
        List<CategoryEntity> categoryEntityList = categoryService.listWithTree();
        return R.ok().put("data", categoryEntityList);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    // @RequiresPermissions("product:category:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    // @RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //  @RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    // @RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category) {
        categoryService.updateById(category);
        return R.ok();
    }

    /**
     * 批量删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
        categoryService.batchRemoveByIds(Arrays.asList(catIds));
        return R.ok();
    }

    // 根据id获取商品的种类信息
    @GetMapping("/info/{catId}")
    public R getCategoryInfoById(@PathVariable("catId") Long catId) {
        CategoryEntity categoryEntity = categoryService.getById(catId);
        return R.ok().put("data", categoryEntity);
    }

    // 批量进行更新
    @PostMapping("/update/sort")
    public R batchUpdate(@RequestBody CategoryEntity[] categoryEntities) {
        categoryService.updateBatchById(Arrays.asList(categoryEntities));
        return R.ok();
    }

}
