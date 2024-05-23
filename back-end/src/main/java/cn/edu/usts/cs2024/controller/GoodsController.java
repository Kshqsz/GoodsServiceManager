package cn.edu.usts.cs2024.controller;

import cn.edu.usts.cs2024.pojo.Goods;
import cn.edu.usts.cs2024.pojo.Result;
import cn.edu.usts.cs2024.service.GoodsService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods")
    public List<Goods> getGoodsByCriteria(String searchName, String searchCategory) {

        return goodsService.getGoodsByCriteria(searchName, searchCategory);
    }

    @PostMapping("/add")
    @Transactional
    public void add(@RequestBody Goods goods) {
        goodsService.add(goods);
    }

    @GetMapping("/page")
    public List<Goods> pageQuery(Integer page, Integer size, String searchName, String searchCategory) {
        return goodsService.pageQuery(page, size, searchName, searchCategory);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        goodsService.deleteById(id);
    }

    @GetMapping("/getById/{id}")
    public Goods getById(@PathVariable Integer id) {
        return goodsService.getById(id);
    }

    @PutMapping("/update")
    public void update(@RequestBody Goods goods) {
        goodsService.update(goods);
    }


    @PostMapping("/export/excel")
    public void exportToExcel(@RequestBody List<Goods> goodsList, HttpServletResponse response) throws IOException {
        goodsService.export(goodsList, response);
    }

    @DeleteMapping("/deleteCategory/{category}")
    public Result deleteCategory(@PathVariable String category) {
        goodsService.deleteCategory(category);
        return Result.success();
    }
}












//    @PostMapping("/api/export/excel")
//    public void exportToExcel(@RequestBody List<Goods> goodsList, HttpServletResponse response) {
//        try {
//            // 设置响应头信息
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename=goods.xlsx");
//
//            // 调用导出方法
//            ExcelUtil.exportToExcel(goodsList, response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



