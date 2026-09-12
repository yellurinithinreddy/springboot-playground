package com.nithin.MultithreadingWork;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
@EnableAsync
public class MultithreadingWorkApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultithreadingWorkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,5,30, TimeUnit.SECONDS
//		, new ArrayBlockingQueue<>(10));
//
//		for(int i=0;i<20;i++){
//			threadPoolExecutor.submit(() -> {
//				log.info("Inside long running task: {}",Thread.currentThread().getName());
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//		}
	}
}
