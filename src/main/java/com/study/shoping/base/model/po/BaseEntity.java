package com.study.shoping.base.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 描述:
 * 公共实体参数
 *
 * @outhor KK
 * @create 2018-07-09 16:15
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    private String remark;
    private boolean active = true;
    private String createdBy = "sys";
    private Date createdAt;
    private String updatedBy = "sys";
    private Date updatedAt;
}