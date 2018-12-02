package com.alibaba.fastcreate.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends Exception {

    private int errorCode;
    private Object[] args;
    private String message;
    private BusinessException linkedException;

    public BusinessException(int errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(int errorCode, Object[] args) {
        this.errorCode = errorCode;
        this.args = args;
    }

    public BusinessException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
