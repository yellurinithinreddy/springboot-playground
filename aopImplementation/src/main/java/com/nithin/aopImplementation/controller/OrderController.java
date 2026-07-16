package com.nithin.aopImplementation.controller;

import com.nithin.aopImplementation.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/order/{itemId}")
    public ResponseEntity<String> orderItem(@PathVariable Long itemId){
        return ResponseEntity.ok(orderService.orderItem(itemId));
    }

    @GetMapping("/return/{itemId}")
    public ResponseEntity<String> returnItem(@PathVariable Long itemId){
        return ResponseEntity.ok(orderService.returnItem(itemId));
    }
}
