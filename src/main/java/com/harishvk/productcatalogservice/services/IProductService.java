package com.harishvk.productcatalogservice.services;

import com.harishvk.productcatalogservice.models.Product;

public interface IProductService {
    Product getProductById(Long id);
}
