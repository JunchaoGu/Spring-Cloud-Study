package com.xidian.nacosservicea.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xidian.nacosservicea.entity.Product;
import com.xidian.nacosservicea.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Student Gu
 * @create: 2026-01-08 11:21
 * @Description: TODO
 **/

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id) throws JsonProcessingException {

        Product product = productService.getProductById(id);

        String result = objectMapper.writeValueAsString(product);

        return result;

    }

}



