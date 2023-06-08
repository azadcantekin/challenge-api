package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.ProductDto;
import com.techcareer.challenge.data.model.ProductModel;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.ProductRepository;
import com.techcareer.challenge.service.ProductService;
import com.techcareer.challenge.utilities.mapper.ModelConverterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelConverterService converterService;

    @Override
    public int addProduct(ProductDto productDto) {
        //fetch data
        Optional<ProductDto> optionalProductDto = Optional.ofNullable(productDto);
        //check if cardDto is empty
        if (optionalProductDto.isEmpty()){
            log.error("Empty productDto received");
            throw new BadRequestException("Bad request");
        }
        //save data to db
        log.info("New product added: {}", productDto.getName());
        ProductModel productModel = converterService.convertToType(optionalProductDto, ProductModel.class);
        productRepository.save(productModel);

        log.info("Product saved with ID: {}", productModel.getId());
        return productModel.getId();


    }

    @Override
    public ProductDto getProduct(Integer productId) {
        //fetch data from db  or throw an exception
        Optional<ProductModel> productModel = Optional.ofNullable(productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found", null)));
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
                log.info("Product sold: {}", productModel.getName());
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
        log.info("Retrieved {} products", productModelList.size());

        return converterService.mapList(productModelList, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        try{
            productRepository.deleteById(productId);
            log.info("Product deleted with ID: {}", productId);
        }
        catch (ResourceNotFoundException exception){
            log.error("Product not found with ID: {}", productId);
            throw new ResourceNotFoundException("Product not found", exception.getCause());
        }
    }
}
