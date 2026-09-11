package com.nithin.learnMultithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class LearnMultithreadingApplication implements CommandLineRunner {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(LearnMultithreadingApplication.class, args);

//		log.info("before worker thread current thread name: {} and state : {}",Thread.currentThread().getName(), Thread.currentThread().getState());
//
//		Thread thread = new Thread(() -> {
//            log.info("inside worker thread current thread name: {} and state : {}",Thread.currentThread().getName(), Thread.currentThread().getState());
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//		});
//
//		thread.start();
//
//		thread.join();
//
//		log.info("after worker thread current thread name: {} and state : {}",Thread.currentThread().getName(),thread.getState());
	}

	@Override
	public void run(String... args) throws Exception {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,6,2,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
		log.info("Executing the method by thread name: {}",Thread.currentThread().getName());
		threadPoolExecutor.submit(() -> {
			log.info("Before long running task executed by thread name {}",Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
			log.info("after long running task executed by thread name {}",Thread.currentThread().getName());

		});

		log.info("Ending the method by thread name: {}",Thread.currentThread().getName());

	}
}
