package com.nithin.learnMultithreading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentInfoService studentInfoService;

    public Student getStudent() throws InterruptedException, ExecutionException {
        log.info("Starting getting the student details, {}",Thread.currentThread().getName());

        long start = System.currentTimeMillis();

        CompletableFuture<String> nameFuture = studentInfoService.getName();
        CompletableFuture<String> collegeFuture = studentInfoService.getCollege();
        CompletableFuture<String> idFuture = studentInfoService.getId();
        CompletableFuture.allOf(nameFuture,collegeFuture,idFuture);
        Student student = new Student(nameFuture.get(),
                collegeFuture.get(),
                idFuture.get());

        long end = System.currentTimeMillis();

        log.info("Time taken to build : {}",end-start);

        return student;

    }
}
