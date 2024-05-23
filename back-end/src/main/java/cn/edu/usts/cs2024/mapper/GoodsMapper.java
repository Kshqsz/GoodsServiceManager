package cn.edu.usts.cs2024.mapper;

import cn.edu.usts.cs2024.pojo.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {
    //@Select("select * from javaee.goods where name like concat('%', #{searchName}, '%') and category = #{searchCategory}")
    List<Goods> getGoodsByCriteria(@Param("searchName") String searchName,
                                   @Param("searchCategory") String searchCategory);

    //@Select("select * from goods where name like concat('%', #{searchName}, '%') and category = #{searchCategory} limit #{start}, #{size}")
    List<Goods> pageQuery(@Param("start") Integer start,
                          @Param("size") Integer size,
                          @Param("searchName") String searchName,
                          @Param("searchCategory") String searchCategory);

    void add(Goods goods);

    @Delete("delete from goods where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from goods where id = #{id}")
    Goods getById(Integer id);

    void update(Goods goods);

    @Delete("delete from goods where category = #{category}")
    void deleteCategory(String category);
}
