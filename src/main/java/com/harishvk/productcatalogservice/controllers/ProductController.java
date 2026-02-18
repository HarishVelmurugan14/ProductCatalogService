package com.harishvk.productcatalogservice.controllers;

import com.harishvk.productcatalogservice.dtos.CategoryDto;
import com.harishvk.productcatalogservice.dtos.ProductDto;
import com.harishvk.productcatalogservice.models.Product;
import com.harishvk.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/product/{id}")
    private ProductDto getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        return convertProductToDto(product);
    }

    @PostMapping("/product")
    private Product createProductById(@RequestBody Product product){
        return product;
    }

    private ProductDto convertProductToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setDescription(product.getCategory().getDescription());
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
