package cn.edu.usts.cs2024.utils;

import cn.edu.usts.cs2024.pojo.Goods;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

public class ExcelUtil {

    public static void exportToExcel(List<Goods> goodsList, OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("商品信息");

        // 创建日期格式
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/MM/dd")); // 设置日期格式为 yyyy/MM/dd

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "商品名称", "类别", "产地", "价格", "生产日期", "生产商"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Goods goods : goodsList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(goods.getId());
            row.createCell(1).setCellValue(goods.getName());
            row.createCell(2).setCellValue(goods.getCategory());
            row.createCell(3).setCellValue(goods.getOrigin());
            row.createCell(4).setCellValue(goods.getPrice().doubleValue()); // 如果价格是 BigDecimal 类型
            Cell dateCell = row.createCell(5);
            // 将日期格式化为 yyyy/MM/dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = dateFormat.format(goods.getProductionDate());
            dateCell.setCellValue(formattedDate);
            dateCell.setCellStyle(dateCellStyle); // 设置日期格式
            row.createCell(6).setCellValue(goods.getManufacturer());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
