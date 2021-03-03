package com.keith.test.boottest.entity;

import lombok.Data;

/**
 * @author xk
 */
@Data
public class BaseEntity  {
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 0-正常,1-已删除
     */
    private Integer delFlag;
    /**
     * 数据唯一码
     */
    private String dataCode;
    /**
     * 创建者ID或名称
     */
    private String createBy;
    /**
     * 更新者ID或名称
     */
    private String updateBy;
}
