package com.nithin.order_service.consumer;

import com.nithin.order_service.entity.OrderStatus;
import com.nithin.order_service.entity.Orders;
import com.nithin.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrdersStatusConsumer {

    private final OrdersRepository ordersRepository;

    @KafkaListener(topics = "order-status-updated")
    @Transactional
    public void consumeOrderStatus(ConsumerRecord<Long, Map<String,Object>> record){
        Long orderId = (Long) record.key();
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("order not found"));
        Map<String, Object> envelope = record.value();
        String status = envelope.get("status").toString();
        Double totalPrice = (Double) envelope.get("totalPrice");

        if("OUT_OF_STOCK".equals(status)) {
            order.setOrderStatus(OrderStatus.OUT_OF_STOCK);
            ordersRepository.save(order);
            return ;
        }

        if("FULFILLED".equals(status)){
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setTotalPrice(totalPrice);
            ordersRepository.save(order);
            return ;
        }
    }
}
