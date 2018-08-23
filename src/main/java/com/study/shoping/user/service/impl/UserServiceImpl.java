package com.study.shoping.user.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.study.shoping.base.service.BaseService;
import com.study.shoping.user.model.dto.UserDto;
import com.study.shoping.user.model.po.RolePo;
import com.study.shoping.user.model.po.UserPo;
import com.study.shoping.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户服务
 * @outhor KK
 * @time 2018-08-09 10:39
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseService implements IUserService {
    private final static Map<Long,UserPo> USERS = Maps.newHashMap();

    static {
        List<RolePo> rolePos = Lists.newArrayList();
        rolePos.add(new RolePo(1L,"admin"));
        Long id = 111L;
        UserPo userPo = new UserPo(id,"admin",DigestUtils.md5DigestAsHex("admin".getBytes()),rolePos);
        USERS.put(id,userPo);

        rolePos = Lists.newArrayList();
        rolePos.add(new RolePo(2L,"user"));
        id = 112L;
        userPo = new UserPo(id,"user",DigestUtils.md5DigestAsHex("user".getBytes()),rolePos);
        USERS.put(id,userPo);

    }

    @Override
    public UserDto findById(Long id) {
        UserPo userPo = USERS.get(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userPo,userDto);
        return userDto;
    }

    @Override
    public UserDto findByUsernameAndPassword(String userName, String password) {
        Collection<UserPo> userPos = USERS.values();
        UserPo userPo = userPos.stream().filter(po -> po.getUsername().equals(userName) && po.getPassword().equals(password)).findAny().orElse(null);
        if(userPo == null)
            return null;
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userPo,userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Collection<UserPo> userPos = USERS.values();
        UserPo userPo = userPos.stream().filter(po -> po.getUsername().equals(userName)).findAny().orElse(null);
        if(userPo == null)
            throw new UsernameNotFoundException("用户名不存在");
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userPo,userDto);
        return userDto;
    }
}