package cn.edu.usts.cs2024.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int id;
    private String name;
    private String category;
    private String origin;
    private BigDecimal price;
    private Date productionDate;
    private String manufacturer;
}
