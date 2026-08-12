package com.nithin.order_service.service;

import com.nithin.order_service.clients.InventoryOpenFeignClient;
import com.nithin.order_service.clients.ShippingOpenFeignClient;

import com.nithin.order_service.dto.OrderRequestDto;
import com.nithin.order_service.dto.OrderRequestItemDto;
import com.nithin.order_service.entity.OrderItem;
import com.nithin.order_service.entity.OrderStatus;
import com.nithin.order_service.entity.Orders;
import com.nithin.order_service.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;
    private final ShippingOpenFeignClient shippingOpenFeignClient;
    private final KafkaTemplate<Long,Object> kafkaTemplate;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all Orders");
        return ordersRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }



    public OrderRequestDto getOrder(Long orderId) {
        log.info("Fetching Order with ID: {}",orderId);
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not Found"));

        return modelMapper.map(order, OrderRequestDto.class);
    }

//    @Transactional
//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRatelimiter",fallbackMethod = "createOrderFallback")
//    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
//    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
//        log.info("Creating order with this details : {}",orderRequestDto);
//
//        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);
//
//        Orders order = modelMapper.map(orderRequestDto,Orders.class);
//        for(OrderItem orderItem:order.getItems()){
//            orderItem.setOrder(order);
//        }
//        order.setTotalPrice(totalPrice);
//        order.setOrderStatus(OrderStatus.CONFIRMED);
//        return modelMapper.map(ordersRepository.save(order), OrderRequestDto.class);
//    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto,Throwable throwable){
        log.error("Got into fall back method for create order: {}",throwable.getMessage());
        return new OrderRequestDto();
    }

    @Transactional
    public OrderRequestDto cancelOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderRequestItemDto> items = order.getItems().stream()
                .map(item -> modelMapper.map(item,OrderRequestItemDto.class))
                .toList();

        OrderRequestDto orderRequestDto = new OrderRequestDto(order.getId(),items,BigDecimal.valueOf(order.getTotalPrice()));

        inventoryOpenFeignClient.addStock(orderRequestDto);

        order.setOrderStatus(OrderStatus.CANCELLED);
        ordersRepository.save(order);


        return orderRequestDto;
    }

//    @Retry(name = "shippingRetry",fallbackMethod = "getShippingStatusFallBack")
    @CircuitBreaker(name = "shippingCircuitBreaker",fallbackMethod = "getShippingStatusFallBack")
    public String getShippingStatus() {
        log.info("get shipping status method called");
        return shippingOpenFeignClient.getShippingStatus();
    }

    public String getShippingStatusFallBack(Throwable throwable){
        log.info("getShippingStatus method failed due to : {}",throwable.getMessage());
        return "get Shipping status fallback method result";
    }



    @Transactional
//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRatelimiter",fallbackMethod = "createOrderFallback")
//    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Creating order with this details : {}",orderRequestDto);

//        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);

        Orders order = modelMapper.map(orderRequestDto,Orders.class);
        List<Long> items = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        for(OrderItem orderItem:order.getItems()){
            orderItem.setOrder(order);
            items.add(orderItem.getProductId());
            quantity.add(orderItem.getQuantity());
        }

        order = ordersRepository.save(order);
        Map<String,Object> envelope = Map.of(
                "items", items,
                "quantity",quantity
        );

        kafkaTemplate.send("order-created-topic",order.getId(),envelope);

        return modelMapper.map(order, OrderRequestDto.class);
    }
}
