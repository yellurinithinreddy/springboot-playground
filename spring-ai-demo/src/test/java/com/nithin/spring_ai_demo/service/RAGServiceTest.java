package com.nithin.spring_ai_demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RAGServiceTest {

    @Autowired
    private RAGService ragService;

    @Test
    public void testGiveAnswerBasedOnTheData(){
        String res = ragService.giveAnswerBasedOnTheData("What is spring boot?");
        System.out.println(res);
    }

    @Test
    public void testIngestPdfData(){
        ragService.ingestPdfData();
    }

}