package com.study.vo;

import com.study.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryVO {
    private Integer id;
    private String name;
    private List<ProductCategoryVO> children;
    private String banner;
    private String top;
    private List<Product> productList;
}
