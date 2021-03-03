package com.keith.test.boottest.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * 店铺商品集合
 *
 * @author keith
 * @since 2021-01-11
 */
@Document(collection = "shop_product_collection")
@Data
public class ShopProductCollection {


    /**
     * 主键id
     */
    @Id
    private String id;

    /**
     * 平台
     */
    @Field("provider_type")
    private String providerType;

    /**
     * 店铺id
     */
    @Field("shop_id")
    private String shopId;

    /**
     * 总条目数
     */
    @Field("total_row")
    private Integer totalRow;

    /**
     * 总页数
     */
    @Field("total_page")
    private Integer totalPage;

    /**
     * 每页条目数
     */
    @Field("page_size")
    private Integer pageSize;

    /**
     * 商品信息<页码,商品>
      */
    @Field("page_product_list")
    private Map<Integer, List<ShopProductBaseInfo>> pageProductList;

    /**
     * 创建时间
     */
    @Field("create_time")
    private Long createTime;

    /**
     * 更新时间
     */
    @Field("update_time")
    private Long updateTime;

}
