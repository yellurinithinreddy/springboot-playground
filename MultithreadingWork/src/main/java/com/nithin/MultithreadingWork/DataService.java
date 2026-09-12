package com.nithin.MultithreadingWork;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataService {


    private final DataHelperService dataHelperService;
    public CombinedData getAllData() throws InterruptedException, ExecutionException {
        log.info("Trying to get all data");
        long start = System.currentTimeMillis();
        CompletableFuture<String> userFuture = dataHelperService.fetchUserData();
        CompletableFuture<String> orderFuture = dataHelperService.fetchOrderData();
        CompletableFuture<String> paymentFuture = dataHelperService.fetchPaymentData();
        CompletableFuture.allOf(userFuture,orderFuture,paymentFuture);
        CombinedData combinedData = new CombinedData(userFuture.get(),orderFuture.get(),paymentFuture.get());
        long end = System.currentTimeMillis();
        log.info("Time taken: {}",end-start);
        return combinedData;
    }
}
