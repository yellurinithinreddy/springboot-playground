package com.nithin.poemGPT.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClient){
        return chatClient
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

}
