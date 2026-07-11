package com.nithin.EmpRules.EmployeeHandBook.controller;

import com.nithin.EmpRules.EmployeeHandBook.service.RAGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RAGController {

    private final RAGService ragService;


    @PostMapping("/askQuestion")
    public ResponseEntity<String> askQuestion(@RequestBody String question){
         return ResponseEntity.ok(ragService.askQuestion(question));
    }
}
