package com.techcareer.challenge.controller;

import com.techcareer.challenge.data.dto.ProductDto;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("add-product")
    public ResponseEntity<Integer> addProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.addProduct(productDto));
    }

    @GetMapping("get-product")
    public ResponseEntity<ProductDto> getProduct(@RequestParam Integer productId){
        if (productId == null || productId < 0) {
            throw new BadRequestException("Invalid product ID");
        }
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping("get-all-between-price")
    public ResponseEntity<List<ProductDto>> getAllBetweenPrice(@RequestParam(required = false) double minPrice
            , @RequestParam(required = false) double maxPrice){
        if (minPrice<0){
            throw new BadRequestException("Bad Request");
        }
        return ResponseEntity.ok(productService.findAllByUnitPriceBetween(minPrice, maxPrice));
    }

    @PostMapping("sell-product")
    public ResponseEntity<Boolean> sellProduct(@RequestParam Integer productId){
        if (productId<0 ){
            throw new BadRequestException("Invalid product ID");
        }
        return ResponseEntity.ok(productService.sellProduct(productId));
    }

    @GetMapping("get-all-product")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("delete-product")
    public void  deleteProduct(@RequestParam Integer productId){
        if (productId<0 ){
            throw new BadRequestException("Invalid product ID");
        }
        productService.deleteProduct(productId);
    }

}
