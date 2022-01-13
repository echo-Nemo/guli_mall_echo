package com.echo.gulimall.product.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.echo.common.utils.PageUtils;
import com.echo.common.utils.Query;

import com.echo.gulimall.product.dao.CategoryDao;
import com.echo.gulimall.product.entity.CategoryEntity;
import com.echo.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {

        //先查出所有分类
        List<CategoryEntity> allCategoryList = baseMapper.selectList(null);

        // 获取该菜单下的一级分类
        List<CategoryEntity> firstCategoryList = allCategoryList.stream().filter(categoryEntity -> categoryEntity
                .getParentCid() == 0).collect(Collectors.toList());

        //父子分类的封装
        List<CategoryEntity> categoryList = firstCategoryList.stream().map(category -> {
            category.setChildrenCategoryList(getChildren(category, allCategoryList));
            return category;
        }).collect(Collectors.toList());

        List<CategoryEntity> assemCategoryList = categoryList.stream().sorted((menu1, menu2) -> {
            int sort = menu1.getSort().compareTo(menu2.getSort());
            return sort;
        }).collect(Collectors.toList());
        return assemCategoryList;
    }

    // 递归查出该分类下的子分类
    public List<CategoryEntity> getChildren(CategoryEntity rootCategory, List<CategoryEntity> allCategory) {
        // 改菜单下的所有子菜单
        List<CategoryEntity> parentCategoryList = allCategory.stream().filter(categoryEntity -> categoryEntity.getParentCid().longValue() == rootCategory.getCatId()).collect(Collectors.toList());

        // 递归进行查找
        final List<CategoryEntity> childrenCategoryList = parentCategoryList.stream().map(category -> {
            category.setChildrenCategoryList(getChildren(category, allCategory));
            return category;
        }).collect(Collectors.toList());

        // 分类根据sort进行排序
        List<CategoryEntity> realCategoryList = childrenCategoryList.stream().sorted((menu1, menu2) -> {
            int sort = menu1.getSort().compareTo(menu2.getSort());
            return sort;
        }).collect(Collectors.toList());

        return realCategoryList;
    }
}