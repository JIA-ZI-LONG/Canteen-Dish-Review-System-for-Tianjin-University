package xyz.tjucomments.tjufood.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 操作日志 Excel 导出 DTO
 */
@Data
public class OperationLogExportDTO {

    @ExcelProperty("日志ID")
    private Long id;

    @ExcelProperty("操作人")
    private String operatorName;

    @ExcelProperty("模块")
    private String module;

    @ExcelProperty("操作")
    private String operation;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("耗时(ms)")
    private Long elapsedTime;

    @ExcelProperty("IP地址")
    private String ip;

    @ExcelProperty("操作时间")
    private String createTime;
}
