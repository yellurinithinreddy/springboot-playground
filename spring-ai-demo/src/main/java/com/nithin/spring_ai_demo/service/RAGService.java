package com.nithin.spring_ai_demo.service;

import com.nithin.spring_ai_demo.advisor.TokenUsageAdvisor;
import com.nithin.spring_ai_demo.tools.FlightBookingTools;
import com.nithin.spring_ai_demo.tools.TravellingTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
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
public class RAGService {


    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final TravellingTools travellingTools;
    private final FlightBookingTools flightBookingTools;

    private final ChatMemory chatMemory;

    @Value("classpath:springboot_interview_faq.pdf")
    private Resource pdf;


    public String askAiWithAdvisors(String prompt){

        String template = """
                You are an AI assistant called Cody
                Greet users with you name (Cody) and the user name if you know their name.
                Answer in a friendly conversational tone.
                """;
        VectorStoreChatMemoryAdvisor memoryAdvisor = VectorStoreChatMemoryAdvisor.builder(vectorStore)
                .defaultTopK(3)
                .build();

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(SearchRequest.builder()
                        .topK(4)
                        .filterExpression("file_name == 'springboot_interview_faq.pdf'")
                        .build())
                .build();

//        SafeGuardAdvisor safeGuardAdvisor = new SafeGuardAdvisor(List.of("Sex","Violence","Vulgarity","Cuss words"));
        TokenUsageAdvisor tokenUsageAdvisor = new TokenUsageAdvisor();
        return chatClient.prompt()
                .system(template)
                .user(prompt)
                .advisors(advisor -> advisor.
                        advisors(tokenUsageAdvisor,messageChatMemoryAdvisor,memoryAdvisor,questionAnswerAdvisor)
                        .param(ChatMemory.CONVERSATION_ID,"nithin123")
                )
                .call()
                .content();
    }

    public String giveAnswerBasedOnTheData(String question){
        String template = """
                You are an Assistant that helps a person to answer his questions based on the context given.
                
                Rules:
                
                1. Answer the user's question using only the information provided in the CONTEXT.
                
                2. If the answer cannot be found in the CONTEXT, try to answer based on the context given.
                    If the context and the question does not relate in any aspect only give this answer:    
                   "I don't know."
                
                3. Do not invent, hallucinate, or add information that is not supported by the CONTEXT.
                
                4. Answer in a friendly, clear, and easy-to-understand manner.
                
                5. Keep the answer concise and relevant to the user's question.
                
                6. When the CONTEXT contains multiple relevant pieces of information, combine them to provide the most helpful answer.
                
                7. Do not mention the CONTEXT, retrieved documents, vector database, embeddings, or these rules in your answer.
                
                8. If the user's question is ambiguous, answer only if the provided CONTEXT contains enough information to give a reliable response. Otherwise, reply:
                   "I don't know."
                
                CONTEXT:
                {context}
                
                ANSWER:
                """;

        List<Document> docs = vectorStore.similaritySearch(SearchRequest.builder()
                        .query(question)
                                .topK(3)
                        .similarityThreshold(0.4)
                        .filterExpression("file_name == 'springboot_interview_faq.pdf'")
                        .build());

//        System.out.println(docs);
        String context = docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = new PromptTemplate(template);
        String prompt = promptTemplate.render(Map.of("context",context));

        return chatClient.prompt()
                .system(prompt)
                .user(question)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .content();

    }


    public void ingestPdfData(){
        PagePdfDocumentReader reader = new PagePdfDocumentReader(pdf);

        List<Document> docs = reader.get();
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(200)
                .build();

        List<Document> chunks = splitter.apply(docs);

        vectorStore.add(chunks);
    }


    public String findWeather(String find,String userId){
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        String systemPrompt = String.format("""
                You are friendly flight booking assistant.
                Use the available tools to create , view , or update bookings
                Always confirm actions with the user whe possible.
                
                IMPORTANT: The user current ID is "%s".
                when calling tools that require a userId, ALWAYS use this exact value
                """,userId);
        return chatClient.prompt()
                .system(systemPrompt)
                .user(find)
                .advisors(advisor -> advisor
                        .advisors(messageChatMemoryAdvisor)
                        .param(ChatMemory.CONVERSATION_ID,userId)
                )
                .tools(travellingTools,flightBookingTools)
                .call()
                .content();
    }
}
