package com.study.shoping.user.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.shoping.utils.R;
import com.study.shoping.user.filter.SecurityCors;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 登出成功
 * @outhor KK
 * @time 2018-08-14 11:51
 */
public class OutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        SecurityCors.setResponse(httpServletResponse);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSONObject.toJSONString(R.ok()));
        out.flush();
        out.close();
    }
}