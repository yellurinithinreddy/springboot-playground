package com.nithin.order_service.controller;

import com.nithin.order_service.config.FeatureEnableConfig;
import com.nithin.order_service.dto.OrderRequestDto;
import com.nithin.order_service.entity.Orders;
import com.nithin.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@RefreshScope
public class OrdersController {

    private final OrdersService ordersService;

    @Value("${my.variable}")
    private String myVariable;

    private final FeatureEnableConfig featureEnableConfig;


    @GetMapping("/helloOrders")
    public String hello(@RequestHeader(value = "X-User-Id") Long userId,@RequestHeader(value = "X-Role") String role){

        if(featureEnableConfig.isFeatureEnabled()){
            return "User tracking of feature wahhhhh: enabled"+myVariable;
        }
        else{
            return "User tracking of feature wohoooo: disabled"+myVariable;
        }

    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(){
        return ResponseEntity.ok(ordersService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long orderId){
        return ResponseEntity.ok(ordersService.getOrder(orderId));
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(ordersService.createOrder(orderRequestDto));
    }

    @PostMapping("/cancel-order/{orderId}")
    public ResponseEntity<OrderRequestDto> cancelOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(ordersService.cancelOrder(orderId));
    }

    @GetMapping("/shippingStatus")
    public ResponseEntity<String> getShippingStatus(){
        return ResponseEntity.ok(ordersService.getShippingStatus());
    }
}
