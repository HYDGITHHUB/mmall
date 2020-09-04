package com.study.exception;

import com.study.enums.ResultEnum;

public class MMallException extends RuntimeException {
    public MMallException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }
}
