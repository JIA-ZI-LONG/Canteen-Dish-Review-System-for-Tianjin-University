// 文件路径: new_yxq/back/src/main/java/xyz/tjucomments/tjufood/utils/excel/ExcelUtil.java

package xyz.tjucomments.tjufood.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ExcelUtil {

    /**
     * 将数据导出为 Excel 文件并直接通过 HttpServletResponse 下载
     *
     * @param response  HttpServletResponse对象
     * @param data      数据列表
     * @param sheetName Sheet页名称
     * @param pojoClass 实体类或DTO类，用于定义表头
     * @param fileName  导出的文件名
     * @throws IOException IO异常
     */
    public static void exportViaWeb(HttpServletResponse response, List<?> data, String sheetName, Class<?> pojoClass, String fileName) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // URL编码文件名，防止中文乱码
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName + ".xlsx");

        // 获取输出流并写入Excel数据
        try (OutputStream outputStream = response.getOutputStream()) {
            EasyExcel.write(outputStream, pojoClass)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(sheetName)
                    .doWrite(data);
        }
    }
}