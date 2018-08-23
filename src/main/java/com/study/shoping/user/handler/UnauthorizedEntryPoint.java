package com.study.shoping.user.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.shoping.utils.R;
import com.study.shoping.user.filter.SecurityCors;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 自定义401返回值
 * @outhor KK
 * @time 2018-08-17 16:45
 */
@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        SecurityCors.setResponse(httpServletResponse);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSONObject.toJSONString(R.error("2",e.getMessage())));
        out.flush();
        out.close();
    }
}