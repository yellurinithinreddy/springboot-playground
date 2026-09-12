package com.nithin.MultithreadingWork;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DummyController {

    private final DataService dataService;
    private final ScheduleService scheduleService;
    @GetMapping(path = "/data")
    public ResponseEntity<CombinedData> getAllData() throws ExecutionException, InterruptedException {
        log.info("Trying to get all the data in controller : {}",Thread.currentThread().getName());
        return ResponseEntity.ok(dataService.getAllData());
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> schedule(@RequestBody MailDto mailDto) {
        return ResponseEntity.ok(scheduleService.scheduleTask(mailDto));
    }
}
