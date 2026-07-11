package com.nithin.EmpRules.EmployeeHandBook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RAGService {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    private final ChatClient chatClient;

    public String askQuestion(String question) {
        String template = """
                You are an AI assistant that answers questions based on the Context provided.
                
                Rules to follow:
                If the answer does not present in the context. Just say I dont know.
                If the answer is related to the context give the answer by combing the whole context
                If the context is larger then summarixe that and give a correct answer based on that.
                
                Context:
                {context}
                
                ANSWER:
                """;

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(question)
                        .topK(4)
                        .filterExpression("file_name == 'policy.pdf'")
                .build());

        String context = docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String prompt = promptTemplate.render(Map.of("context",context));
        System.out.println(prompt);

        return chatClient.prompt()
                .system(prompt)
                .user(question)
                .call()
                .content();

    }
}
