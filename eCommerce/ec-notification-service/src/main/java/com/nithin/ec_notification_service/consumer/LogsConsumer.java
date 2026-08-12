package com.nithin.ec_notification_service.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class LogsConsumer {


    @KafkaListener(topics = {
            "order-created-topic",
            "order-status-updated"
    })
    public void logAllEvents(ConsumerRecord<Long, Map<String,Object>> record){
        Long orderId = record.key();
        Map<String,Object> envelope = record.value();

        log.info("envelope : {}, orderId : {}",envelope,orderId);
    }

}
