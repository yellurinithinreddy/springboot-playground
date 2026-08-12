package com.nithin.user_service.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void publish(String message,int key){
        kafkaTemplate.send("user-random-topic",""+key%2,message);
    }
}
