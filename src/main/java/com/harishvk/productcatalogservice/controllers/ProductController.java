package com.harishvk.productcatalogservice.controllers;

import com.harishvk.productcatalogservice.dtos.CategoryDto;
import com.harishvk.productcatalogservice.dtos.ProductDTO;
import com.harishvk.productcatalogservice.models.Category;
import com.harishvk.productcatalogservice.models.Product;
import com.harishvk.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/product/{id}")
    private ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        ProductDTO productDTO = convertProductToDTO(product);
        ResponseEntity<ProductDTO> output = new ResponseEntity<>(productDTO, HttpStatus.OK);
        return output;
    }

    @GetMapping("/products")
    private List<ProductDTO> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product:products){
            productDTOs.add(convertProductToDTO(product));
        }
        return productDTOs;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> replaceProductById(@PathVariable Long id, @RequestBody ProductDTO input) {
        Product product = convertDTOToProduct(input);
        Product output = productService.replaceProductById(id, product);
        ProductDTO productDTO = convertProductToDTO(output);
        ResponseEntity<ProductDTO> entity = new ResponseEntity<>(productDTO, HttpStatus.OK);
        return entity;
    }

    @PostMapping("/product")
    private Product createProductById(@RequestBody Product product){
        return product;
    }

    private Product convertDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setId(productDTO.getId());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        if(productDTO.getCategory() != null) {
            Category category = new Category();
            category.setDescription(productDTO.getCategory().getDescription());
            category.setName(productDTO.getCategory().getName());
            category.setId(productDTO.getCategory().getId());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDTO convertProductToDTO(Product product) {
        ProductDTO productDto = new ProductDTO();
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
