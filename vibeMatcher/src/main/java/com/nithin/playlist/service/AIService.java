package com.nithin.playlist.service;

import lombok.RequiredArgsConstructor;
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
public class AIService {

    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public String getSongAccToVibe(String feeling,String genre) {
        SearchRequest.Builder requestBuilder = SearchRequest.builder()
                .query(feeling)
                .topK(4)
                .similarityThreshold(0.3);

        if(genre != null && !genre.isBlank()){
            requestBuilder.filterExpression("genre == '"+ genre + "'");
        }

        List<Document> docs = vectorStore.similaritySearch(requestBuilder.build());
        System.out.println(docs);
        return docs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }


    public void ingestSongsDataIntoDB(){
        List<Document> documents = List.of(
                new Document("""
        Song: Fix You
        Artist: Coldplay
        Meaning: A comforting song about supporting someone who feels broken, hopeless, exhausted, or defeated. It expresses the desire to help a person recover from failure, sadness, and emotional pain and encourages them to keep going.
        """, Map.of(
                        "song", "Fix You",
                        "artist", "Coldplay",
                        "genre", "Rock"
                )),

                new Document("""
        Song: Believer
        Artist: Imagine Dragons
        Meaning: A powerful song about transforming pain, suffering, criticism, and difficult experiences into personal strength, motivation, and resilience.
        """, Map.of(
                        "song", "Believer",
                        "artist", "Imagine Dragons",
                        "genre", "Rock"
                )),

                new Document("""
        Song: The Scientist
        Artist: Coldplay
        Meaning: A melancholic song about regret, relationship mistakes, emotional confusion, and wishing to return to the beginning to repair something that went wrong.
        """, Map.of(
                        "song", "The Scientist",
                        "artist", "Coldplay",
                        "genre", "Rock"
                )),

                new Document("""
        Song: Demons
        Artist: Imagine Dragons
        Meaning: A song about struggling with inner darkness, personal flaws, emotional battles, and trying to protect someone you love from your own problems.
        """, Map.of(
                        "song", "Demons",
                        "artist", "Imagine Dragons",
                        "genre", "Rock"
                )),

                new Document("""
        Song: Numb
        Artist: Linkin Park
        Meaning: A song about emotional exhaustion, pressure from expectations, losing your identity, frustration, and feeling disconnected from yourself and others.
        """, Map.of(
                        "song", "Numb",
                        "artist", "Linkin Park",
                        "genre", "Rock"
                )),

                new Document("""
        Song: Happy
        Artist: Pharrell Williams
        Meaning: An energetic and optimistic song about happiness, positivity, confidence, celebration, and enjoying life regardless of negative circumstances.
        """, Map.of(
                        "song", "Happy",
                        "artist", "Pharrell Williams",
                        "genre", "Pop"
                )),

                new Document("""
        Song: Roar
        Artist: Katy Perry
        Meaning: An empowering song about overcoming fear, finding confidence, standing up for yourself, becoming stronger, and refusing to stay silent.
        """, Map.of(
                        "song", "Roar",
                        "artist", "Katy Perry",
                        "genre", "Pop"
                )),

                new Document("""
        Song: Someone Like You
        Artist: Adele
        Meaning: An emotional song about heartbreak, accepting the end of a relationship, remembering lost love, sadness, and trying to move forward.
        """, Map.of(
                        "song", "Someone Like You",
                        "artist", "Adele",
                        "genre", "Pop"
                )),

                new Document("""
        Song: Shake It Off
        Artist: Taylor Swift
        Meaning: An upbeat song about ignoring criticism, negativity, rumors, and judgment while continuing to enjoy life and remain confident.
        """, Map.of(
                        "song", "Shake It Off",
                        "artist", "Taylor Swift",
                        "genre", "Pop"
                )),

                new Document("""
        Song: Firework
        Artist: Katy Perry
        Meaning: An inspirational song about recognizing your self-worth, overcoming insecurity, expressing your potential, and believing that you can achieve meaningful things.
        """, Map.of(
                        "song", "Firework",
                        "artist", "Katy Perry",
                        "genre", "Pop"
                ))
        );

        vectorStore.add(documents);
    }
}
