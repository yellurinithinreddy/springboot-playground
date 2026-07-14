package com.nithin.SafeAssistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClient){
        return chatClient
                .build();
    }

    @Bean
    ChatMemory messageChatMemoryAdvisor(JdbcChatMemoryRepository chatMemory){
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemory)
                .maxMessages(3)
                .build();
    }
}
