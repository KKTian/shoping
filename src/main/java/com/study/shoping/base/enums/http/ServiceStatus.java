package com.study.shoping.base.enums.http;

public enum ServiceStatus implements IServiceStatus {
    SUCCESS("0","成功"),
    /**
     * 其他
     */
    WRITE_ERROR("500","其他错误"),
    PARAM_VALID_ERROR("501","请求参数验证错误"),

    /**
     * 错误的请求
     */
    REQUEST_NULL("400", "请求有错误"),
    NOT_FOUND("404", "未找到页面"),
    SERVER_ERROR("500", "服务器异常"),
    ACCESS_DENIED("4", "无访问权限");

    ServiceStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
