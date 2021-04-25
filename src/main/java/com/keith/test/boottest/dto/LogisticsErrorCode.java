package com.keith.test.boottest.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 物流错误码
 *
 * @author keith
 * @since 2021-03-26
 */
@Data
public class LogisticsErrorCode {
    @Excel(name = "是否可编辑异常（1可编辑，2不可编辑）")
    private String isEdit;
    @Excel(name = "派维标准错误代码(1XXX/2XXX/3XXX/4XXX/5XXX)")
    private String errorCode;
    @Excel(name = "派维标准错误代码描述")
    private String errorMessage;
    private String enMessage;
}
