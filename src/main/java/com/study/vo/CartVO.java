package com.study.vo;

import lombok.Data;

@Data
public class CartVO {
    private Integer id;
    private String name;
    private Float price;
    private String fileName;
    private Integer stock;
    private Integer quantity;
    private Float cost;
    private Integer productId;
}
