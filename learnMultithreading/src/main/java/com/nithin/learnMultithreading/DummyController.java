package com.nithin.learnMultithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class DummyController {

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


}
