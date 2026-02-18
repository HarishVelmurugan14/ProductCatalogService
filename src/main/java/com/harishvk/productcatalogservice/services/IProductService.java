package com.harishvk.productcatalogservice.services;

import com.harishvk.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
}
