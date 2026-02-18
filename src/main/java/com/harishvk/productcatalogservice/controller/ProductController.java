package com.harishvk.productcatalogservice.controller;

import com.harishvk.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/product/{id}")
    private String getProductById(@PathVariable Long id){
        return "Hello";
    }

    @PostMapping("/product")
    private Product createProductById(@RequestBody Product product){
        return product;
    }
}
