package com.study.vo;

import lombok.Data;

@Data
public class AddressVO {
    private Integer userid;
    private String address;
    private String remark;
    private Boolean isdefault;
}
