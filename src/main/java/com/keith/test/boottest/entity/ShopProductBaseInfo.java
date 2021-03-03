package com.keith.test.boottest.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 店铺商品基本信息
 *
 * @author keith
 * @since 2021-01-14
 */
@Data
public class ShopProductBaseInfo {
    /**
     * 商品id
     */
    @Field("goods_id")
    private String goodsId;
    /**
     * 商品编码
     */
    @Field("goods_code")
    private String goodsCode;
    /**
     * 商品名称
     */
    @Field("goods_name")
    private String goodsName;
    /**
     * 图片链接
     */
    @Field("pic_url")
    private String picUrl;
    /**
     * 商品链接
     */
    @Field("goods_link")
    private String goodsLink;
    /**
     * 商品来源平台
     */
    @Field("goods_prefix")
    private String goodsPrefix;
    /**
     * 原价
     */
    @Field("unit_price")
    private Price unitPrice;
    /**
     * 促销价
     */
    @Field("pro_price")
    private Price proPrice;
}
