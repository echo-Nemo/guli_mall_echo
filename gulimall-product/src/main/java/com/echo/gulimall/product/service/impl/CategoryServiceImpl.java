package com.echo.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.echo.gulimall.product.vo.Catalog2Vo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * 使用redis缓存菜单下的一级分类
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> firstCategoryList = new ArrayList<>();

        //先查出所有分类
        List<CategoryEntity> allCategoryList = baseMapper.selectList(null);

        // 获取该菜单下的一级分类
        String firstLevel = redisTemplate.opsForValue().get("firstLevel");
        // 采用本地锁 进行加锁 并将查询到的数据放到redis中
        synchronized (this) {
            // 缓存中没有数据
            if (StringUtils.isBlank(firstLevel)) {
                System.out.println("在数据库中进行查询");
                firstCategoryList = allCategoryList.stream().filter(categoryEntity -> categoryEntity
                        .getParentCid() == 0).collect(Collectors.toList());
                // 将一级分类转化为json
                String firstCategoryJson = JSON.toJSONString(firstCategoryList);
                redisTemplate.opsForValue().set("firstLevel", firstCategoryJson);
            }
        }

        //  JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalogs2Vo>>>(){});
        System.out.println("使用缓存进行查询");
        firstCategoryList = JSON.parseObject(firstLevel, new TypeReference<List<CategoryEntity>>() {
        });


        //父子分类的封装
        List<CategoryEntity> categoryList = firstCategoryList.stream().map(category -> {
            category.setChildren(getChildren(category, allCategoryList));
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
            category.setChildren(getChildren(category, allCategory));
            return category;
        }).collect(Collectors.toList());

        // 分类根据sort进行排序
        List<CategoryEntity> realCategoryList = childrenCategoryList.stream().sorted((menu1, menu2) -> {
            int sort = menu1.getSort().compareTo(menu2.getSort());
            return sort;
        }).collect(Collectors.toList());

        return realCategoryList;
    }


//    @Override
//    public Map<String, List<Catalog2Vo>> getCatalogJson() {
//        // 1.从缓存中读取分类信息
//        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
//        if (StringUtils.isEmpty(catalogJSON)) {
//            // 2. 缓存中没有，查询数据库
//            Map<String, List<Catalog2Vo>> catalogJsonFromDB = getCatalogJsonFromDB();
//            // 3. 查询到的数据存放到缓存中，将对象转成 JSON 存储
//            redisTemplate.opsForValue().set("catalogJSON", JSON.toJSONString(catalogJsonFromDB));
//            return catalogJsonFromDB;
//        }
//        return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catalog2Vo>>>() {
//        });
//    }

//    /**
//     * 加缓存前,只读取数据库的操作
//     *
//     * @return
//     */
//    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDB() {
//        System.out.println("查询了数据库");
//
//        // 性能优化：将数据库的多次查询变为一次
//        List<CategoryEntity> selectList = this.baseMapper.selectList(null);
//
//        //1、查出所有分类
//        //1、1）查出所有一级分类
//        List<CategoryEntity> level1Categories = getParentCid(selectList, 0L);
//
//        //封装数据
//        Map<String, List<Catalog2Vo>> parentCid = level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
//            //1、每一个的一级分类,查到这个一级分类的二级分类
//            List<CategoryEntity> categoryEntities = getParentCid(selectList, v.getCatId());
//
//            //2、封装上面的结果
//            List<Catalog2Vo> Catalog2Vos = null;
//            if (categoryEntities != null) {
//                Catalog2Vos = categoryEntities.stream().map(l2 -> {
//                    Catalog2Vo Catalog2Vo = new Catalog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());
//
//                    //1、找当前二级分类的三级分类封装成vo
//                    List<CategoryEntity> level3Catelog = getParentCid(selectList, l2.getCatId());
//
//                    if (level3Catelog != null) {
//                        List<Catalog2Vo.catalog3List> category3Vos = level3Catelog.stream().map(l3 -> {
//                            //2、封装成指定格式
//                            Catalog2Vo.Category3Vo category3Vo = new Catalog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
//
//                            return category3Vo;
//                        }).collect(Collectors.toList());
//                        Catalog2Vo.setCatalog3List(category3Vos);
//                    }
//
//                    return Catalog2Vo;
//                }).collect(Collectors.toList());
//            }
//
//            return Catalog2Vos;
//        }));
//
//        return parentCid;
//    }


    // 根据id进行删除，如果有商品和其关联，就先不能删除
    @Override
    public void batchRemoveByIds(List<Long> asList) {
        //TODO 检查当前的菜单是否被别的地方所引用
        baseMapper.deleteBatchIds(asList);
    }

}