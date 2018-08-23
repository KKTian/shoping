package com.study.shoping.utils;

import com.study.shoping.base.enums.http.IServiceStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 统一返回封装
 * @outhor KK
 * @time 2018-08-03 14:50
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", "0");
        put("message", "操作成功");
    }

    public static R error() {
        return error("1", "操作失败");
    }

    public static R error(String msg) {
        return error("500", msg);
    }

    public static R error(String code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("message", msg);
        return r;
    }

    public static R error(IServiceStatus serviceStatus) {
        return error(serviceStatus.getCode(),serviceStatus.getMessage());
    }

    public static R error(IServiceStatus serviceStatus,String notes) {
        return error(serviceStatus.getCode(),serviceStatus.getMessage(),notes);
    }

    public static R error(String code, String msg,String notes) {
        R r = R.error(code,msg);
        r.put("notes", notes);
        return r;
    }

    public static R valid(int totalError,Object valids) {
        R r = R.error("1003","验证错误");
        r.put("totalError", totalError);
        r.put("valids",valids);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("message", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}