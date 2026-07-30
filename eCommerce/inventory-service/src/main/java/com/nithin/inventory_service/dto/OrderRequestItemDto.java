package com.nithin.inventory_service.dto;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    Long productId;
    Integer quantity;
}
