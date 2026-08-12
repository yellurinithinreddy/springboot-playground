package com.nithin.inventory_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderStatusUpdated(){
        return new NewTopic("order-status-updated",3,(short) 1);
    }
}
