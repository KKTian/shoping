package com.study.shoping.user.security;

import com.study.shoping.user.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: provider
 * @outhor KK
 * @time 2018-08-13 10:27
 */
public class AuthenticationProviderCustom implements AuthenticationProvider {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        String username = token.getName();
        UserDetails userDetails = null;
        if(StringUtils.isNotBlank(username)){
            userDetails = iUserService.loadUserByUsername(username);
        }
        if(userDetails == null) {
            throw new UsernameNotFoundException("用户名/密码无效");
        }else if (!userDetails.isEnabled()){
            throw new DisabledException("用户已被禁用");
        }else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("账号已过期");
        }else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("账号已被锁定");
        }else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException("凭证已过期");
        }
        if(!passwordEncoder.matches((String)token.getCredentials(),userDetails.getPassword())){
            throw new BadCredentialsException("用户名/密码错误");
        }
        //授权
        return new UsernamePasswordAuthenticationToken(userDetails, token.getCredentials(),userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}