package com.techcareer.challenge.service;

import com.techcareer.challenge.data.dto.ProductDto;

import java.util.List;

public interface ProductService {
    int addProduct(ProductDto productDto);
    ProductDto getProduct(Integer productId);
    List<ProductDto> findAllByUnitPriceBetween(double minPrice, double maxPrice);
    boolean sellProduct(Integer productId);
    List<ProductDto> getAllProducts();
    void deleteProduct(Integer productId);
}
