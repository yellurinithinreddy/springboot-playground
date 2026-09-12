package com.nithin.learnMultithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduler {

//    @Scheduled(fixedRate = 2000,initialDelay = 5000)
//    @Scheduled(cron = "0/5 * * * * *") // every 5 seconds it will run
    public void logMe() {
        log.info("Starting Scheduler1 {}",Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Ending Scheduler1 {}",Thread.currentThread().getName());
    }

//    @Scheduled(fixedDelay = 200)
//    @Async
    public void logYou() {
        log.info("Starting Scheduler2 {}",Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Ending Scheduler2 {}",Thread.currentThread().getName());
    }
}
