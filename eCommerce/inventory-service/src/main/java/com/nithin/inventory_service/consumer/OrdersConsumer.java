package com.nithin.inventory_service.consumer;

import com.nithin.inventory_service.dto.OrderRequestDto;
import com.nithin.inventory_service.dto.OrderRequestItemDto;
import com.nithin.inventory_service.entity.Product;
import com.nithin.inventory_service.repository.ProductRepository;
import com.nithin.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrdersConsumer {

    private final StockHandler stockHandler;
    private final KafkaTemplate<Long,Object> kafkaTemplate;

    @KafkaListener(topics = "order-created-topic")
    public void consumerOrders(ConsumerRecord<Long, Map<String,Object>> record){
        Map<String,Object> envelope = record.value();

        List<?> items = (List<?>) envelope.get("items");
        List<?> quantities = (List<?>) envelope.get("quantity");
        Long orderId = record.key();
        try{

            stockHandler.reduceStocks(
                    items.stream()
                            .map(value -> ((Number) value).longValue())
                            .toList(),

                    quantities.stream()
                            .map(value -> ((Number) value).intValue())
                            .toList(),
                    orderId
            );
        }catch(Exception e){
            Map<String,Object> status_envelope = Map.of(
                    "orderId",orderId,
                    "status", "OUT_OF_STOCK",
                    "totalPrice",null
            );
            kafkaTemplate.send("order-status-updated",orderId,status_envelope);
        }

    }



}
