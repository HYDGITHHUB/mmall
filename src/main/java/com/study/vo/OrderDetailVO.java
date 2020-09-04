package com.study.vo;

import lombok.Data;

@Data
public class OrderDetailVO {
    private Integer quantity;
    private Float cost;
    private String fileName;
    private String name;
    private Float price;
}
