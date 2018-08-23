package com.study.shoping.user.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.shoping.utils.R;
import com.study.shoping.user.filter.SecurityCors;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 授权失败
 * @outhor KK
 * @time 2018-08-14 10:26
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        SecurityCors.setResponse(httpServletResponse);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSONObject.toJSONString(R.error(e.getMessage())));
        out.flush();
        out.close();
    }
}