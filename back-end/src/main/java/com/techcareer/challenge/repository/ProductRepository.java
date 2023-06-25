package com.techcareer.challenge.repository;

import com.techcareer.challenge.data.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductModel , Integer> {
    List<ProductModel> findAllByUnitPriceBetween(double minPrice, double maxPrice);
}
