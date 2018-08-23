package com.study.shoping.user.filter;

import javax.servlet.http.HttpServletResponse;

/**
 * @description: security跨域
 * @outhor KK
 * @time 2018-08-17 16:25
 */
public class SecurityCors {
    public static void setResponse(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }
}