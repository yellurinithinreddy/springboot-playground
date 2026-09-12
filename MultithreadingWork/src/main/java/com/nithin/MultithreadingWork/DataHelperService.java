package com.nithin.MultithreadingWork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class DataHelperService {


    @Async
    public CompletableFuture<String> fetchUserData() throws InterruptedException {
        log.info("Getting user data : {}",Thread.currentThread().getName());
        Thread.sleep(2000);
        return CompletableFuture.completedFuture("User Data");
    }

    @Async
    public CompletableFuture<String> fetchOrderData() throws InterruptedException {
        log.info("Getting order data : {}",Thread.currentThread().getName());
        Thread.sleep(3000);
        return CompletableFuture.completedFuture("order Data");
    }

    @Async
    public CompletableFuture<String> fetchPaymentData() throws InterruptedException {
        log.info("Getting payment data : {}",Thread.currentThread().getName());
        Thread.sleep(4000);
        return CompletableFuture.completedFuture("payment Data");
    }
}
