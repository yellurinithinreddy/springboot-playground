package com.nithin.order_service.clients;

import com.nithin.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "inventory-service",path ="/inventory",url = "${INVENTORY_SERVICE_URI:}")
public interface InventoryOpenFeignClient {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(OrderRequestDto orderRequestDto);

    @PutMapping("/products/addStock")
    Void addStock(OrderRequestDto orderRequestDto);
}
