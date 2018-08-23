package com.study.shoping.user.security;

import com.study.shoping.user.filter.AuthenticationFilter;
import com.study.shoping.user.filter.JWTRequestFilter;
import com.study.shoping.user.handler.*;
import com.study.shoping.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.DigestUtils;

/**
 * @description: Spring Security
 * @outhor KK
 * @time 2018-08-09 14:41
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(-1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService iUserService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthenticationProviderCustom();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
          .authenticationProvider(authenticationProvider());
//            .userDetailsService(iUserService)
//                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return DigestUtils.md5DigestAsHex(charSequence.toString().getBytes());
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(DigestUtils.md5DigestAsHex(charSequence.toString().getBytes()));
            }
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/*.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.headers().frameOptions().disable();
//        http.headers().addHeaderWriter(new XContentTypeOptionsHeaderWriter());
        http
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessHandler(new OutSuccessHandler())
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/druid/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();

        http.addFilterAt(authenticationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtRequestFilter(),UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).accessDeniedHandler(accessDeniedHandler());
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        http.addFilterBefore(filter,CsrfFilter.class);
        http.headers().cacheControl();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter();
        filter.setPostOnly(true);
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        filter.setAuthenticationFailureHandler(new LoginFailureHandler());
        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/users/login","POST"));
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public JWTRequestFilter jwtRequestFilter(){
        return new JWTRequestFilter(iUserService,accessDeniedHandler());
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint(){
        return new UnauthorizedEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new SysAccessDeniedHandler();
    }

}