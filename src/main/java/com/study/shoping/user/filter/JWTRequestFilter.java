package com.study.shoping.user.filter;

import com.study.shoping.base.exception.AuthException;
import com.study.shoping.tools.Globals;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: JWT验证
 * @outhor KK
 * @time 2018-08-17 17:47
 */
public class JWTRequestFilter extends OncePerRequestFilter {
    private UserDetailsService userDetailsService;
    private AccessDeniedHandler accessDeniedHandler;

    public JWTRequestFilter(UserDetailsService userDetailsService,AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication;
        try {
            authentication = getAuthentication(header);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }catch (AuthException e){
            accessDeniedHandler.handle(request,response,e);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            String username;
            try {
                Jws<Claims> jws = Jwts.parser()
                        .setSigningKey(Globals.JWT_SECRET)
                        .parseClaimsJws(token.replace("Bearer ", ""));
                username = jws.getBody().getSubject();
            }
            catch (ExpiredJwtException e){
                throw new AuthException("用户已失效",e.getCause());
            }catch (UnsupportedJwtException e){
                throw new AuthException("授权方式错误",e.getCause());
            }catch (MalformedJwtException e){
                throw new AuthException("授权异常",e.getCause());
            }catch (SignatureException e){
                throw new AuthException("签名错误",e.getCause());
            }catch (IllegalArgumentException e){
                throw new AuthException("其他授权错误",e.getCause());
            }
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(userDetails == null) {
                    throw new UsernameNotFoundException("用户名或密码错误");
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
                authentication.setDetails(userDetails);
                return authentication;
            }
            return null;
        }
        return null;
    }
}