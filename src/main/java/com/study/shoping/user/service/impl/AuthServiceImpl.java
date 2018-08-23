package com.study.shoping.user.service.impl;

import com.study.shoping.base.service.BaseService;
import com.study.shoping.user.model.po.UserPo;
import com.study.shoping.user.service.IAuthService;
import org.springframework.stereotype.Service;

/**
 * @description: 权限服务
 * @outhor KK
 * @time 2018-08-09 10:32
 */
@Service
public class AuthServiceImpl extends BaseService implements IAuthService {




    @Override
    public UserPo register(UserPo userPoToAdd) {
        return null;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }
}