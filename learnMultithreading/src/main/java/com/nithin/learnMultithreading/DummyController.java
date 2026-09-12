package com.nithin.learnMultithreading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DummyController {

    private final StudentService studentService;
    @GetMapping("/hello")
    public ResponseEntity<String> hello() throws InterruptedException {
        log.info("Thread Blocked: {}",Thread.currentThread().getName());
        Thread.sleep(2000);
        return ResponseEntity.ok("Nithin");
    }

    @GetMapping("/hello-cf")
    public CompletableFuture<ResponseEntity<String>> helloCF() throws InterruptedException {
        log.info("Thread Called: {}",Thread.currentThread().getName());

        CompletableFuture<ResponseEntity<String>> cf =  CompletableFuture.supplyAsync(() -> {
            log.info("Inside Future");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok("Nithin");
        });

        cf.thenAccept((entity) -> {
            log.info("the name is: {}",entity.getBody());
        });

        return cf;
    }

    @GetMapping("/student")
    public ResponseEntity<Student> getStudent() throws InterruptedException, ExecutionException {
        log.info("Get Student API called: {}",Thread.currentThread().getName());;
        return ResponseEntity.ok(studentService.getStudent());
    }





}
