package com.nithin.user_service.controller;

import com.nithin.user_service.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @PostMapping("/{message}")
    public ResponseEntity<String> publish(@PathVariable String message){

        for(int i=0;i<1000;i++){

            kafkaProducer.publish(message+i,i);
        }
        return ResponseEntity.ok("Message sent");
    }
}
