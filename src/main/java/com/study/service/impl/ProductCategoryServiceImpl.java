package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.ProductCategory;
import com.study.mapper.ProductCategoryMapper;
import com.study.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.service.ProductService;
import com.study.vo.ProductCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductService productService;

    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
        //查询一级分类
        List<ProductCategory> levelOneList = getProductCategoryByType(1,0);
        List<ProductCategoryVO> result = new ArrayList<>();
        ProductCategoryVO productCategoryVOOne;
        int num = 0;
        for (ProductCategory categoryLevelOne : levelOneList) {
            productCategoryVOOne = new ProductCategoryVO();
            productCategoryVOOne.setBanner("banner"+num+".png");
            productCategoryVOOne.setTop("top"+num+".png");
            num++;
            BeanUtils.copyProperties(categoryLevelOne, productCategoryVOOne);
            productCategoryVOOne.setProductList(this.productService.findAllByProductCategory(1,productCategoryVOOne.getId()));
            //转换children，查询该一级分类对应的二级分类
            List<ProductCategory> levelTwoList = getProductCategoryByType(2,productCategoryVOOne.getId());
            //转VO
            List<ProductCategoryVO> levelTwoVOList = new ArrayList<>();
            ProductCategoryVO productCategoryVOTwo;
            for (ProductCategory categoryLevelTwo : levelTwoList) {
                productCategoryVOTwo = new ProductCategoryVO();
                BeanUtils.copyProperties(categoryLevelTwo, productCategoryVOTwo);
                //转换children，查询该二级分类对应的三级分类
                List<ProductCategory> levelThreeList = getProductCategoryByType(3,productCategoryVOTwo.getId());
                //转VO
                List<ProductCategoryVO> levelThreeVOList = new ArrayList<>();
                ProductCategoryVO productCategoryVOThree;
                for (ProductCategory categoryLevelThree : levelThreeList) {
                    productCategoryVOThree = new ProductCategoryVO();
                    BeanUtils.copyProperties(categoryLevelThree, productCategoryVOThree);
                    levelThreeVOList.add(productCategoryVOThree);
                }
                productCategoryVOTwo.setChildren(levelThreeVOList);
                levelTwoVOList.add(productCategoryVOTwo);
            }
            productCategoryVOOne.setChildren(levelTwoVOList);
            result.add(productCategoryVOOne);
        }
        return result;
    }

    public List<ProductCategory> getProductCategoryByType(Integer type,Integer parentId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type", type);
        wrapper.eq("parent_id", parentId);
        return this.productCategoryMapper.selectList(wrapper);
    }
}
