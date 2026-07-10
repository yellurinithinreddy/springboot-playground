package com.nithin.spring_ai_demo.controller;

import com.nithin.spring_ai_demo.service.RAGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final RAGService ragService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String question, @RequestParam String userId){
        return ResponseEntity.ok(ragService.findWeather(question,userId));
    }
}
