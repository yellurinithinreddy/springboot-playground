package com.nithin.poemGPT.controller;

import com.nithin.poemGPT.dto.Poem;
import com.nithin.poemGPT.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {


    private final ChatService chatService;

    @GetMapping("/poem")
    public ResponseEntity<Poem> poem(@RequestParam String topic, @RequestParam String lang){
        return ResponseEntity.ok(chatService.poem(topic,lang));

    }
}
