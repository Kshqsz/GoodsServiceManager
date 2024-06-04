package cn.edu.usts.cs2024.service.impl;

import cn.edu.usts.cs2024.mapper.GoodsMapper;
import cn.edu.usts.cs2024.pojo.Goods;
import cn.edu.usts.cs2024.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsByCriteria(String searchName,
                                          String searchCategory) {
        List<Goods> goods = goodsMapper.getGoodsByCriteria(searchName, searchCategory);
        return goods;
    }

    @Override
    public List<Goods> pageQuery(Integer page, Integer size, String searchName, String searchCategory) {
        Integer start = (page - 1) * size;
        List<Goods> goods = goodsMapper.pageQuery(start, size, searchName, searchCategory);
        return goods;
    }

    @Override
    public void add(Goods goods) {
        goodsMapper.add(goods);
    }

    @Override
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    @Override
    public Goods getById(Integer id) {
        return goodsMapper.getById(id);
    }

    @Override
    public void update(Goods goods) {
        goodsMapper.update(goods);
    }

    @Override
    public void export(List<Goods> goodsList, HttpServletResponse response) throws IOException {
        OutputStream out = response.getOutputStream();

        XSSFWorkbook excel = new XSSFWorkbook();

        XSSFSheet sheet = excel.createSheet();
        XSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("名称");
        row.createCell(2).setCellValue("种类");
        row.createCell(3).setCellValue("产地");
        row.createCell(4).setCellValue("价格");
        row.createCell(5).setCellValue("生产日期");
        row.createCell(6).setCellValue("生产商");

        int size = goodsList.size();
        for (int i = 0; i < size; i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(goodsList.get(i).getId());
            sheet.setColumnWidth(1, 12 * 256);
            row.createCell(1).setCellValue(goodsList.get(i).getName());
            row.createCell(2).setCellValue(goodsList.get(i).getCategory());
            row.createCell(3).setCellValue(goodsList.get(i).getOrigin());
            row.createCell(4).setCellValue(goodsList.get(i).getPrice().doubleValue());
            Cell dateCell = row.createCell(5);
            sheet.setColumnWidth(5, 15 * 256);
            // 将日期格式化为 yyyy/MM/dd
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = sdf.format(goodsList.get(i).getProductionDate());
            dateCell.setCellValue(formattedDate);

            row.createCell(6).setCellValue(goodsList.get(i).getManufacturer());
        }
        try {
            response.setContentType("application//vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=goods_data.xlsx");
            excel.write(out);
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(String category) {
        goodsMapper.deleteCategory(category);
    }

    @Override
    public List<Goods> getGoodsByCategory(String category) {
        return goodsMapper.getGoodsByCategory(category);
    }
}
