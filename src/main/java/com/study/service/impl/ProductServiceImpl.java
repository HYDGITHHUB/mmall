package com.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.Product;
import com.study.mapper.ProductMapper;
import com.study.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findAllByProductCategory(Integer type,Integer levelId) {
        QueryWrapper wrapper = new QueryWrapper();
        String column = "";
        switch (type){
            case 1:
                column = "categorylevelone_id";
                break;
            case 2:
                column = "categoryleveltwo_id";
                break;
            case 3:
                column = "categorylevelthree_id";
                break;
        }
        wrapper.eq(column, levelId);
        return this.productMapper.selectList(wrapper);
    }
}
