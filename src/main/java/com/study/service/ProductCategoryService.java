package com.study.service;

import com.study.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
public interface ProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();
}
