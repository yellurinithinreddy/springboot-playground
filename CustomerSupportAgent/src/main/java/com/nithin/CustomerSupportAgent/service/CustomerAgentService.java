package com.nithin.CustomerSupportAgent.service;

import com.nithin.CustomerSupportAgent.tools.NetworkTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerAgentService {


    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;
    private final NetworkTools networkTools;

    @Value("classpath:NovaLink_R500_User_Manual.pdf")
    private Resource pdfFile;

    public String chat(String chat) {
        String template = """
                You are an AI assistant that answers based on the given context.
                If the question is not present in the context , please say I dont know.
                
                Context:
                {Context}
                
                Answer in a friendly conversational tone.
                """;

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(chat)
                        .topK(4)
                        .filterExpression("file_name == 'NovaLink_R500_User_Manual.pdf'")
                        .build());
        String context = docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String systemPrompt = promptTemplate.render(Map.of("Context",context));

        VectorStoreChatMemoryAdvisor vectorStoreChatMemoryAdvisor = VectorStoreChatMemoryAdvisor.builder(vectorStore)
                .defaultTopK(8)
                .build();

        return chatClient.prompt()
                .system(systemPrompt)
                .user(chat)
                .advisors(advisor -> advisor
                        .advisors(vectorStoreChatMemoryAdvisor)
                                .param(ChatMemory.CONVERSATION_ID,"nithin123")
                        )
                .tools(networkTools)
                .call()
                .content();
    }


    public void ingestPdfData(){
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfFile);

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document> docs = splitter.apply(pagePdfDocumentReader.get());
        vectorStore.add(docs);
    }
}
