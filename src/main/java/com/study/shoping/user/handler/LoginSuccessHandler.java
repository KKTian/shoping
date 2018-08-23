package com.study.shoping.user.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.study.shoping.tools.Globals;
import com.study.shoping.utils.R;
import com.study.shoping.user.filter.SecurityCors;
import com.study.shoping.user.model.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @description: 授权成功
 * @outhor KK
 * @time 2018-08-14 10:25
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        String token = Jwts.builder()
                .setSubject(userDto.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000))
                .signWith(SignatureAlgorithm.HS512, Globals.JWT_SECRET)
                .compact();
//                        httpServletResponse.addHeader("Authorization", "Bearer " + token);
        Map<String,Object> data = Maps.newHashMap();
        data.put("token",token);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        SecurityCors.setResponse(httpServletResponse);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(JSONObject.toJSONString(R.ok().put("data",data)));
        out.flush();
        out.close();
    }
}