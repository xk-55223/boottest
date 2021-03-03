package com.keith.test.boottest.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 有道语言模板
 *
 * @author keith
 * @since 2021-02-01
 */
@Data
public class YouDaoLangTemplate {
    @Excel(name = "英文名")
    private String enName;
    @Excel(name = "中文名")
    private String cnName;
    @Excel(name = "代码")
    private String code;
}
