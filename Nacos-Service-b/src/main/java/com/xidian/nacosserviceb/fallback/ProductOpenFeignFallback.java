package com.xidian.nacosserviceb.fallback;

import com.xidian.entity.Product;
import com.xidian.nacosserviceb.feign.ProductOpenFeign;

/**
 * @author: Student Gu
 * @create: 2026-01-10 00:21
 * @Description: TODO
 **/

public class ProductOpenFeignFallback implements ProductOpenFeign {
    @Override
    public Product getProduct(Long id) {
        Product product = new Product();
        product.setProductName("兜底回调名字");
        return product;
    }
}



