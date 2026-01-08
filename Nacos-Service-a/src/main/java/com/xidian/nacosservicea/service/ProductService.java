package com.xidian.nacosservicea.service;

import com.xidian.nacosservicea.entity.Product;
import org.springframework.stereotype.Repository;

/**
 * @author: Student Gu
 * @create: 2026-01-08 11:25
 * @Description: TODO
 **/
@Repository
public interface ProductService {
    public Product getProductById(Long id);
}



