package com.study.shoping.base.exception;

import com.study.shoping.base.enums.http.IServiceStatus;

/**
 * 描述: 运行时异常类
 *
 * @outhor gaobitian
 * @create 2018-05-31 17:30
 */
public class ShopingRunException extends RuntimeException {
    private String code;

    private String message;

    public ShopingRunException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ShopingRunException(String code, String message, Throwable cause) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public ShopingRunException(IServiceStatus serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public ShopingRunException(IServiceStatus serviceExceptionEnum, Throwable cause) {
        super(cause);
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}