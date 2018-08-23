package com.study.shoping.user.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @description: 用户实体类
 * @outhor KK
 * @time 2018-08-09 10:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPo  {
    private Long id;
    private String username;
    private String password;
    private List<RolePo> roles;
}