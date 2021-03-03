package com.keith.test.boottest.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 翻译模板
 *
 * @author keith
 * @since 2021-01-30
 */
@Data
public class TranslateTemplate {
    @Excel(name = "source")
    private String source;
    @Excel(name = "target")
    private String target;
}
