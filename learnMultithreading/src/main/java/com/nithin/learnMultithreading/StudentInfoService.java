package com.nithin.learnMultithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class StudentInfoService {


    @Async
    public CompletableFuture<String> getName() throws InterruptedException {
        log.info("Getting the name, {}",Thread.currentThread().getName());
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("Nithin");
    }

    @Async
    public CompletableFuture<String> getCollege() throws InterruptedException {
        log.info("Getting the College, {}",Thread.currentThread().getName());
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("SVEC");
    }

    @Async
    public CompletableFuture<String> getId() throws InterruptedException {
        log.info("Getting the id, {}",Thread.currentThread().getName());
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("20121A12C9");
    }
}
