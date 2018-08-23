package com.study.shoping.user.controller;

import com.google.common.collect.Maps;
import com.study.shoping.base.BaseController;
import com.study.shoping.user.model.dto.UserDto;
import com.study.shoping.utils.R;
import com.study.shoping.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @description: 用户
 * @outhor KK
 * @time 2018-07-25 10:28
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/users/")
public class UserController extends BaseController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("info")
    public R info(){
        UserDto user = currentUserDetail();
        Map<String,Object> data = Maps.newHashMap();
        data.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name",user.getUsername());
//        List<String> roles = Lists.newArrayList();
//        roles.add("admin");
        data.put("roles",user.getRoles());
        return R.ok().put("data",data);
    }

}