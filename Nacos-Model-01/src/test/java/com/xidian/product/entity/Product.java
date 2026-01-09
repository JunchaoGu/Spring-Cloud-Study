package com.xidian.product.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Student Gu
 * @create: 2026-01-08 11:20
 * @Description: TODO
 **/
@Data
public class Product {
    private Long id;
    private BigDecimal price;
    private String productName;
    private int num;
}




