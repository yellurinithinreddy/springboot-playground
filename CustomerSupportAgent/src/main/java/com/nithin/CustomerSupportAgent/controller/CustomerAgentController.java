package com.nithin.CustomerSupportAgent.controller;

import com.nithin.CustomerSupportAgent.service.CustomerAgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerAgentController {

    private final CustomerAgentService customerAgentService;

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody String chat){
        return ResponseEntity.ok(customerAgentService.chat(chat));
    }
}
