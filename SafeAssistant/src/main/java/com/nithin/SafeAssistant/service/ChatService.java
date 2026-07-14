package com.nithin.SafeAssistant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public String chat(String chat, String userId) {

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();
        SafeGuardAdvisor safeGuardAdvisor = new SafeGuardAdvisor(List.of("competitor"));
        SimpleLoggerAdvisor simpleLoggerAdvisor = new SimpleLoggerAdvisor();

        return chatClient.prompt()
                .system("You are an AI assistant that normally chats in a friendly conversational tone and reply")
                .user(chat)
                .advisors(advisor -> advisor.
                            advisors(safeGuardAdvisor,simpleLoggerAdvisor,messageChatMemoryAdvisor)
                                .param(ChatMemory.CONVERSATION_ID,userId)
                        )
                .call()
                .content();


    }
}
