// 文件路径: new_yxq/back/src/main/java/xyz/tjucomments/tjufood/dto/AuditLogExportDTO.java

package xyz.tjucomments.tjufood.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

@Data
public class AuditLogExportDTO {

    @ExcelProperty("日志ID")
    private Long id;

    @ExcelProperty("操作人")
    private String operatorName;

    @ExcelProperty("模块")
    private String module;

    @ExcelProperty("操作类型")
    private String operation;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("耗时(ms)")
    private Long executeTime;

    @ExcelProperty("IP地址")
    private String ip;

    @ExcelProperty(value = "操作时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private String createTime;
}