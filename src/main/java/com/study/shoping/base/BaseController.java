package com.study.shoping.base;

import com.study.shoping.user.model.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BaseController {

    public UserDto currentUserDetail(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getDetails();
        return principal == null ? null : (UserDto) principal;
    }
}
