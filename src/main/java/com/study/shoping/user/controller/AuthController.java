package com.study.shoping.user.controller;

import com.study.shoping.base.BaseController;
import com.study.shoping.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 权限
 * @outhor KK
 * @time 2018-07-25 10:28
 */
@Slf4j
@RestController
@RequestMapping("/auth/")
public class AuthController extends BaseController {

    @PreAuthorize("hasRole('admin')")
    @GetMapping("hello")
    public R hello(){
        System.out.println("auth");
        return R.ok();
    }

    @PreAuthorize("hasRole('user')")
    @GetMapping("hello2")
    public R hello2(){
        System.out.println("auth2");
        return R.ok();
    }

}