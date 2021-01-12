package com.keith.test.boottest.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * <p>
 * 商品是否存在记录
 * </p>
 *
 * @author keith
 * @since 2020-12-22
 */
@Data
public class ProductExist {


    private Integer id;

    /**
     * 平台，如TMALL,TB
     */
    @Excel(name = "platform")
    private String platform;

    /**
     * 商品编码
     */
    @Excel(name = "goods_code")
    private String goodsCode;

    /**
     * 该商品是否存在 0不存在 1存在
     */
    private Boolean exist;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 是否删除 0正常 1删除
     */
    private Integer delFlag;

}
