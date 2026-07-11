package com.nithin.EmpRules.EmployeeHandBook.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClient){
        return chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
