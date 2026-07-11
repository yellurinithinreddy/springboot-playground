package com.nithin.poemGPT.service;

import com.nithin.poemGPT.dto.Poem;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    public Poem poem(String topic, String lang) {
        return chatClient.prompt()
                .system("you are a Sarcastic Poet and write poems based on the topic and language given")
                .user("The Topic and Language given are " + topic + ", " + lang)
                .call()
                .entity(Poem.class);
    }
}

