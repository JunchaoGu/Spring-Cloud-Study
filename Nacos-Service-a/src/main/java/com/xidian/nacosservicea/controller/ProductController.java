package com.xidian.nacosservicea.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xidian.entity.Product;
import com.xidian.nacosservicea.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable Long id) throws JsonProcessingException {

        Product product = productService.getProductById(id);


        return product;

    }

    /**
     * 从远程获取 商品，微服务之间调用；
     * @return
     */
    private String getProductFromRemote(){

        List<ServiceInstance> instances = discoveryClient.getInstances("Nacos-Service-a");

//        可能会考虑到负载均衡，向第一个实例发送请求来查询商品
        ServiceInstance serviceInstance = instances.get(0);
        int port = serviceInstance.getPort();
        String host = serviceInstance.getHost();
        String url = "http://"+host+ ":"+port;

        return null;

    }

}



