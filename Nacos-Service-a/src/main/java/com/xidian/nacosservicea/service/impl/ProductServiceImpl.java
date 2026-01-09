package com.xidian.nacosservicea.service.impl;

import com.xidian.entity.Product;
import com.xidian.nacosservicea.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author: Student Gu
 * @create: 2026-01-08 11:27
 * @Description: TODO
 **/
@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public Product getProductById(Long id) {

        Product product = new Product();
        product.setProductName("123");
        product.setNum(1);
        product.setPrice(BigDecimal.valueOf(123));
        product.setId(id);

        return product;
    }
}



