package com.nithin.spring_ai_demo.service;

import com.nithin.spring_ai_demo.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {


    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;


    public float[] embedding(String text){
        return embeddingModel.embed(text);
    }

    public void save(){
        List<Document> movies = List.of(

                new Document(
                        "Inception is a science fiction movie about a skilled thief who enters people's dreams to steal information and is given a mission to plant an idea inside someone's mind.",
                        Map.of(
                                "title", "Inception",
                                "genre", "Science Fiction",
                                "year", 2010
                        )
                ),

                new Document(
                        "The Dark Knight is a superhero crime movie where Batman faces the Joker, a criminal mastermind who creates chaos in Gotham City and challenges Batman's moral principles.",
                        Map.of(
                                "title", "The Dark Knight",
                                "genre", "Action",
                                "year", 2008
                        )
                ),

                new Document(
                        "Interstellar is a science fiction movie about astronauts traveling through a wormhole in space to search for a new habitable planet as Earth becomes increasingly unable to support human life.",
                        Map.of(
                                "title", "Interstellar",
                                "genre", "Science Fiction",
                                "year", 2014
                        )
                )
        );

        List<Document> documents = List.of(

                new Document(
                        "Spring Boot is a Java framework used to build backend applications and REST APIs.",
                        Map.of(
                                "category", "backend",
                                "technology", "Spring Boot",
                                "language", "Java"
                        )
                ),

                new Document(
                        "Apache Kafka is a distributed event streaming platform used for asynchronous communication between microservices.",
                        Map.of(
                                "category", "messaging",
                                "technology", "Apache Kafka",
                                "type", "event-streaming"
                        )
                ),

                new Document(
                        "PGVector is a PostgreSQL extension used to store embeddings and perform vector similarity search.",
                        Map.of(
                                "category", "database",
                                "technology", "PGVector",
                                "database", "PostgreSQL"
                        )
                ),

                new Document(
                        "Docker is a containerization platform used to package applications and their dependencies into portable containers.",
                        Map.of(
                                "category", "devops",
                                "technology", "Docker",
                                "type", "containerization"
                        )
                ),

                new Document(
                        "Redis is an in-memory data store commonly used for caching, session management, and improving application performance.",
                        Map.of(
                                "category", "database",
                                "technology", "Redis",
                                "type", "in-memory"
                        )
                ),

                new Document(
                        "Spring Security is a Java security framework used to implement authentication, authorization, JWT, and OAuth2 in backend applications.",
                        Map.of(
                                "category", "security",
                                "technology", "Spring Security",
                                "language", "Java"
                        )
                )
        );
        vectorStore.add(movies);
        vectorStore.add(documents);
    }


    public List<Document> similaritySearch(String text){
//        return vectorStore.similaritySearch(text);
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(text)
                        .topK(3)
                        .similarityThreshold(0.6)
                        .build());
    }


    public String getJoke(String topic){
        String text = """
            Write a poem which has four lines only
            Don't make jokes on politics
            This is the topic you should write a poem on {topic}
            """;

    PromptTemplate promptTemplate = new PromptTemplate(text);
        String prompt = promptTemplate.render(Map.of("topic",topic));
        Joke joke=   chatClient.prompt()
//                .system("Give the joke in two lines")
                .user(prompt)
                .advisors(
                        new SimpleLoggerAdvisor()
                )
                .call()
                .entity(Joke.class);
//                .content();

        return joke.text();
    }
}
