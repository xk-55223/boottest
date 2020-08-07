package com.keith.test.boottest.entity;

import com.keith.test.boottest.enums.LanguageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author eden
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogisticsService extends BaseEntity {

    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 服务代码
     */
    private String serviceCode;
    /**
     * 供应商编码
     */
    private String providerCode;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 服务代码 关联pw_logistics_service_type
     */
    private String serviceTypeCode;
    /**
     * 标准物流服务编码
     */
    private String standardServiceCode;
    /**
     * 物流商是否支持API接口对接 1支持 0不支持
     */
    private Integer isSupportApi;
    /**
     * 审核编码
     */
    private String auditCode;
    /**
     * 信息变更状态
     */
    private Integer auditStatus;
    /**
     * 信息变更状态描述
     */
    private String auditStatusComment;
    /**
     * 语言类型：zh中文，en英文
     */
    private LanguageEnum lang;
    /**
     * 物流产品logo
     */
    private String logo;
    /**
     * 中文名
     */
    private String serviceName;
    /**
     * 当地承运商
     */
    private String localCarrier;
    /**
     * 物流轨迹查询页面
     */
    private String trackPage;
    /**
     * 联系电话
     */
    private String contact;
    /**
     * 禁寄商品
     */
    private String restrictedGoods;
    /**
     * 产品特点
     */
    private String feature;
    /**
     * 最小运送时间（工作日）
     */
    private Integer minTimeInTransit;
    /**
     * 最大运送时间（工作日）
     */
    private Integer maxTimeInTransit;
    /**
     * 1有效 0无效
     */
    private Byte valid;
    /**
     * 保险费率
     */
    private Integer premiumRate;
    /**
     * 最低保额
     */
    private Integer minInsuredAmount;
    /**
     * 最高保额
     */
    private Integer maxInsuredAmount;
    /**
     * 盈利率(%)
     */
    private Integer profitRate;
    /**
     * 体积重基数 Volumetric Weight = Length x Width x Height / Volumetric Divisor（0表示不算体积重）
     */
    private Integer dimensionalParam;
    /**
     * 最高限重
     */
    private Integer weightHighLimit;
    /**
     * 最低限重
     */
    private Integer weightLowLimit;
    /**
     * 是否包税
     */
    private Byte isTariffCover;
    /**
     * 是否支持DDU
     */
    private Byte isSupportDdu;
    /**
     * 是否支持DDP
     */
    private Byte isSupportDdp;
    /**
     * 排序序号
     */
    private Integer sortNum;

}