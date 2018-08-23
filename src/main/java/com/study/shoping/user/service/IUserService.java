package com.study.shoping.user.service;

import com.study.shoping.user.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UserDto findById(Long id);

    UserDto findByUsernameAndPassword(String userName,String password);
}