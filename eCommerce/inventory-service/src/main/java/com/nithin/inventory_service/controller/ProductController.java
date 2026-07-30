package com.nithin.inventory_service.controller;

import com.nithin.inventory_service.clients.OrdersOpenFeignClient;
import com.nithin.inventory_service.dto.OrderRequestDto;
import com.nithin.inventory_service.dto.ProductDto;
import com.nithin.inventory_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrdersOpenFeignClient ordersOpenFeignClient;

    @GetMapping("/fetchOrders")
    public String fetch(HttpServletRequest request){
//        ServiceInstance instance = discoveryClient.getInstances("order-service").getFirst();
//
//        return restClient.get()
//                .uri(instance.getUri()+"/orders/core/helloOrders")
//                .retrieve()
//                .body(String.class);

        return ordersOpenFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductByID(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(productService.reduceStocks(orderRequestDto));
    }

    @PutMapping("/addStock")
    public ResponseEntity<Void> addStock(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(productService.addStock(orderRequestDto));
    }
}
