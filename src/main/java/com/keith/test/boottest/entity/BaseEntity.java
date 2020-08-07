package com.keith.test.boottest.entity;

import lombok.Data;

@Data
public class BaseEntity  {
    private Long createTime;
    private Long updateTime;
    private Integer delFlag;
    private String dataCode;
    private String createBy;
    private String updateBy;
}
