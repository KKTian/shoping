package com.study.shoping.user.model.dto;

import com.google.common.collect.Lists;
import com.study.shoping.user.model.po.RolePo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 用户实体
 * @outhor KK
 * @time 2018-08-09 15:18
 */
@Data
public class UserDto implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private List<RolePo> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = Lists.newArrayList();
        List<String> rolePos = this.getRoles();
        rolePos.stream().forEach(s ->
                auths.add(new SimpleGrantedAuthority("ROLE_"+s))
        );
        return auths;
    }

    public List<String> getRoles() {
        return roles.stream().map(po-> po.getName()).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}