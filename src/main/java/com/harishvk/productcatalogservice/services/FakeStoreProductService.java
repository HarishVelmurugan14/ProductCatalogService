package com.harishvk.productcatalogservice.services;

import com.harishvk.productcatalogservice.dtos.FakeStoreProductDTO;
import com.harishvk.productcatalogservice.models.Category;
import com.harishvk.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
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

    @Override
    public Product replaceProductById(Long id, Product product) {
        FakeStoreProductDTO input = convertProductToDTO(product);
        ResponseEntity<FakeStoreProductDTO> responseEntity = requestForEntity(HttpMethod.PUT, "https://fakestoreapi.com/products/{id}", input, FakeStoreProductDTO.class, id);
        if(responseEntity.hasBody() && responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null){
            return convertDtoToProduct(responseEntity.getBody());
        }
        return null;
    }

    public <T> ResponseEntity<T> requestForEntity(HttpMethod method, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate =  restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, method, requestCallback, responseExtractor, uriVariables);
    }


    private FakeStoreProductDTO convertProductToDTO(Product product) {
        FakeStoreProductDTO fakeStoreDTO = new FakeStoreProductDTO();
        fakeStoreDTO.setId(product.getId());
        fakeStoreDTO.setTitle(product.getName());
        fakeStoreDTO.setDescription(product.getDescription());
        fakeStoreDTO.setPrice(product.getPrice());
        fakeStoreDTO.setImage(product.getImageUrl());
        if(product.getCategory() != null){
            fakeStoreDTO.setCategory(product.getCategory().getName());
        }
        return fakeStoreDTO;
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
