package com.study.shoping.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.shoping.user.model.vo.LoginVo;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description:  AuthenticationFilter that supports rest login(json login) and form login.
 * @outhor KK
 * @time 2018-08-13 17:48
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //attempt Authentication when Content-Type is json
        String contentType = request.getContentType();
        if(contentType != null && (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE))){

            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()){
                LoginVo authenticationBean = mapper.readValue(is,LoginVo.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
            }catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }else {
            return super.attemptAuthentication(request, response);
        }
    }
}