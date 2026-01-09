package com.xidian.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Student Gu
 * @create: 2026-01-09 10:15
 * @Description: TODO
 **/

@Data
public class Order {

    private Long id;
    private BigDecimal totalAmount;
    private Long userId;
    private String nickName;
    private String address;
    private List<Object> productList;


}



