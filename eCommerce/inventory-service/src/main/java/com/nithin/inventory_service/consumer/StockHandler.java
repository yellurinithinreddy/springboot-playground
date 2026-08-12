package com.nithin.inventory_service.consumer;

import com.nithin.inventory_service.entity.Product;
import com.nithin.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StockHandler {

    private final ProductRepository productRepository;
    private final KafkaTemplate<Long,Object> kafkaTemplate;
    @Transactional
    public Double reduceStocks(List<Long> items, List<Integer> quantities, Long orderId) {
        Double totalPrice = 0.0;
        for(int i=0;i<items.size();i++){
            Long productId = items.get(i);
            Integer quantity = quantities.get(i);

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("No product found"));

            if(product.getStock() < quantity){

                throw new RuntimeException("out of stock");
            }
            product.setStock(product.getStock()-quantity);
            totalPrice += product.getPrice()*quantity;
            productRepository.save(product);
        }
        Map<String,Object> envelope = Map.of(
                "orderId",orderId,
                "status", "FULFILLED",
                "totalPrice",totalPrice
        );

        kafkaTemplate.send("order-status-updated",orderId,envelope);

        return totalPrice;
    }
}
