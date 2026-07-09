package com.nithin.spring_ai_demo.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

public class TokenUsageAdvisor implements CallAdvisor {
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        // Continue the advisor chain and call the LLM
        ChatClientResponse response =
                callAdvisorChain.nextCall(chatClientRequest);

        ChatResponse chatResponse = response.chatResponse();

        if (chatResponse != null) {

            Usage usage = chatResponse
                    .getMetadata()
                    .getUsage();

            Integer inputTokens = usage.getPromptTokens();
            Integer outputTokens = usage.getCompletionTokens();
            Integer totalTokens = usage.getTotalTokens();

            System.out.println("Input Tokens  : " + inputTokens);
            System.out.println("Output Tokens : " + outputTokens);
            System.out.println("Total Tokens  : " + totalTokens);
        }

        return response;
    }

    @Override
    public String getName() {
        return "TokenUsageAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
