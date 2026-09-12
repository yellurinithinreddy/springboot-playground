package com.nithin.MultithreadingWork;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DummyController {

    private final DataService dataService;
    @GetMapping(path = "/data")
    public ResponseEntity<CombinedData> getAllData() throws ExecutionException, InterruptedException {
        log.info("Trying to get all the data in controller : {}",Thread.currentThread().getName());
        return ResponseEntity.ok(dataService.getAllData());
    }
}
