package com.keith.test.boottest.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import lombok.Data;

/**
 * 帮助中心
 *
 * @author keith
 * @since 2020-11-30
 */
@Data
public class Help extends ExcelExportEntity {
    /**
     * 主键id
     */
    private long id;
    /**
     * 文章id
     */
    @Excel(name = "文章ID")
    private long helpId;
    /**
     * 分类id
     */
    private long categoryId;
    /**
     * 文章标题
     */
    @Excel(name = "文章标题")
    private String helpTitle;
    /**
     * 文章内容
     */
    @Excel(name = "文章内容")
    private String helpContent;
    /**
     * 文章时间
     */
    private long helpTime;
    /**
     * 应该是浏览次数
     */
    private long views;
    /**
     * 有帮助数量
     */
    private long helpnum;
    /**
     * 无帮助数量
     */
    private long nohelpnum;
    /**
     * 热点类型
     */
    private int hotType;
    /**
     * 排序
     */
    private int sort;
    /**
     * 语言id
     */
    private long languageId;
    /**
     * 类目名
     */
    private String categoryName;
    /**
     * 父类ID
     */
    private long parentCateId;
    /**
     * 父类名
     */
    private String parentCateName;
    /**
     * 展示平台，1PC，2APP
     */
    private int platform;
    /**
     * 帮助描述
     */
    @Excel(name = "帮助描述")
    private String helpDesc;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 是否为重构后的内容 0否 1是
     */
    private Integer isNew;
}
