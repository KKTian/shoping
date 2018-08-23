package com.study.shoping.base.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 描述:
 *
 * @outhor KK
 * @create 2018-06-01 12:50
 */
@Data
@AllArgsConstructor
public class ValidVo {
    private String name;
    private Object rejectedValue;
    private String validMsg;
}