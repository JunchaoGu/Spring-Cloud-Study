package com.xidian.nacosserviceb.feign;

import com.xidian.entity.Product;
import com.xidian.nacosserviceb.fallback.ProductOpenFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Student Gu
 * @create: 2026-01-09 22:49
 * @Description: TODO
 **/

@FeignClient(value = "Nacos-Service-a",fallback = ProductOpenFeignFallback.class)
public interface ProductOpenFeign {

    @GetMapping("/product/{id}")
    Product getProduct(@PathVariable("id") Long id);

}



