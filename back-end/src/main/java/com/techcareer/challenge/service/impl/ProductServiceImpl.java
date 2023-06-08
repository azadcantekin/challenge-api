package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.ProductDto;
import com.techcareer.challenge.data.model.ProductModel;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.ProductRepository;
import com.techcareer.challenge.service.ProductService;
import com.techcareer.challenge.utilities.mapper.ModelConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelConverterService converterService;

    @Override
    public int addProduct(ProductDto productDto) {
        //fetch data
        Optional<ProductDto> optionalProductDto = Optional.ofNullable(productDto);
        //check if cardDto is empty
        if (optionalProductDto.isEmpty()){
            throw new BadRequestException("Bad request");
        }
        //save data to db
        ProductModel productModel = converterService.convertToType(optionalProductDto, ProductModel.class);
        productRepository.save(productModel);

        return productModel.getId();


    }

    @Override
    public ProductDto getProduct(Integer productId) {
        //fetch data from db  or throw an exception
        Optional<ProductModel> productModel = Optional.ofNullable(productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found", null)));
        //map and return
        return converterService.convertToType(productModel , ProductDto.class);
    }

    @Override
    public List<ProductDto> findAllByUnitPriceBetween(double minPrice, double maxPrice) {
        List<ProductModel> productModelList = productRepository.findAllByUnitPriceBetween(minPrice , maxPrice);
        return converterService.mapList(productModelList, ProductDto.class);
    }

    @Override
    public boolean sellProduct(Integer productId) {
        //Fetch data from db
        Optional<ProductModel> optionalProductModel = Optional.ofNullable(productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found", null)));
        if (optionalProductModel.isPresent()){
            ProductModel productModel = optionalProductModel.get();

            //check if units in stock greater than 0 and return true
            if (productModel.getUnitsInStock()>0){
                productModel.setUnitsInStock(productModel.getUnitsInStock()-1);
                productRepository.save(productModel);
                return true;
            }

        }
        return false;

    }

    @Override
    public List<ProductDto> getAllProducts() {
        // Fetch data from db  , mapping dto and return list .
        List<ProductModel> productModelList = productRepository.findAll();
        return converterService.mapList(productModelList, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        try{
            productRepository.deleteById(productId);
        }
        catch (ResourceNotFoundException exception){
            throw new ResourceNotFoundException("Product not found", exception.getCause());
        }
    }
}
