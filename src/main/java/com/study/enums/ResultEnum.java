package com.study.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    USER_NOT_EXIST(0,"用户为空"),
    ORDER_NOT_EXIST(1,"订单为空"),
    USER_SAVE_FAIL(2,"用户保持失败");

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
}
