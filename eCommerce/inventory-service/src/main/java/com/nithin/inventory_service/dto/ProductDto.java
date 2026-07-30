package com.nithin.inventory_service.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;

    private Double price;

    private Integer stock;
}
