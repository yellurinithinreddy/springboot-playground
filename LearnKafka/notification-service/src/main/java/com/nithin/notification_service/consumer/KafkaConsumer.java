package com.nithin.notification_service.consumer;


import com.nithin.event.UserCreatedEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {


    @KafkaListener(topics = "user-random-topic")
    public void consumer1(String message){

        System.out.println("Consumer 1: "+message);
    }

    @KafkaListener(topics = "user-random-topic")
    public void consumer2(String message){

        System.out.println("Consumer 2: "+message);
    }

    @KafkaListener(topics = "user-random-topic")
    public void consumer3(String message){

        System.out.println("Consumer 3: "+message);
    }

    @KafkaListener(topics = "user-created-topic")
    public void consumer4(UserCreatedEvent userCreatedEvent){

        System.out.println("event: "+userCreatedEvent);
    }
}
