package com.nithin.playlist.controller;

import com.nithin.playlist.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    @GetMapping("/match-vibe")
    public ResponseEntity<String> getSongAccToVibe(@RequestParam String feeling,
                                                   @RequestParam(required = false) String genre){
        return ResponseEntity.ok(aiService.getSongAccToVibe(feeling,genre));
    }



}
