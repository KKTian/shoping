package com.study.shoping.user.service;

import com.study.shoping.user.model.po.UserPo;

public interface IAuthService {
    UserPo register(UserPo userPoToAdd);

    String login(String username,String password);

    String refresh(String oldToken);
}
