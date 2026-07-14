package com.nithin.SafeAssistant.controller;

import com.nithin.SafeAssistant.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String chat, @RequestParam String userId){
        return ResponseEntity.ok(chatService.chat(chat,userId));
    }
}
