package cn.edu.usts.cs2024.service;

import cn.edu.usts.cs2024.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface GoodsService {

    List<Goods> getGoodsByCriteria(String searchName,String searchCategory);

    List<Goods> pageQuery(Integer page, Integer size, String searchName, String searchCategory);

    void add(Goods goods);

    void deleteById(Integer id);

    Goods getById(Integer id);

    void update(Goods goods);

    //void export(List<Goods> goodsList, HttpServletResponse response) throws IOException;

    void deleteCategory(String category);

    List<Goods> getGoodsByCategory(String category);
}
