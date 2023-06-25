package com.techcareer.challenge.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private int id;
    private String name;
    private String description;
    private double unitPrice;
    private int unitsInStock;
}
