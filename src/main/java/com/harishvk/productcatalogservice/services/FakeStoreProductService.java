package com.harishvk.productcatalogservice.services;

import com.harishvk.productcatalogservice.dtos.FakeStoreProductDTO;
import com.harishvk.productcatalogservice.dtos.ProductDTO;
import com.harishvk.productcatalogservice.models.Category;
import com.harishvk.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDTO fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}",
                FakeStoreProductDTO.class, id);
        return convertDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<List<FakeStoreProductDTO>> response = restTemplate.exchange("https://fakestoreapi.com/products",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<FakeStoreProductDTO>>() {
                });

        List<FakeStoreProductDTO> fakeStoreProductDTOs = response.getBody();
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO fakeStoreProductDTO : fakeStoreProductDTOs) {
            products.add(convertDtoToProduct(fakeStoreProductDTO));
        }
        return products;
    }


    private Product convertDtoToProduct(FakeStoreProductDTO fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}
