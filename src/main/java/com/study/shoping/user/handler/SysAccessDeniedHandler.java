package com.study.shoping.user.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.shoping.utils.R;
import com.study.shoping.user.filter.SecurityCors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 错误访问
 * @outhor KK
 * @time 2018-08-17 16:23
 */
@Component
public class SysAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        SecurityCors.setResponse(httpServletResponse);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSONObject.toJSONString(R.error("1",e.getMessage())));
        out.flush();
        out.close();
    }
}