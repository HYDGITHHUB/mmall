package com.study.service;

import com.study.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author southwind
 * @since 2020-08-17
 */
public interface ProductService extends IService<Product> {
    public List<Product> findAllByProductCategory(Integer type,Integer levelId);
}
