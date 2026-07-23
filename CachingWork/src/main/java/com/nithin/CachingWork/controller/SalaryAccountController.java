package com.nithin.CachingWork.controller;

import com.nithin.CachingWork.entity.SalaryAccount;
import com.nithin.CachingWork.service.SalaryAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/salary")
public class SalaryAccountController {

    private final SalaryAccountService salaryAccountService;

    @PutMapping("/incrementBalance/{accountId}")
    public ResponseEntity<SalaryAccount> incrementBalance(@PathVariable Long accountId){
        return ResponseEntity.ok(salaryAccountService.increment(accountId));
    }
}
