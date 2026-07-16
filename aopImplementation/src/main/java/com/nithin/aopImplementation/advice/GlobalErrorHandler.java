package com.nithin.aopImplementation.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class GlobalErrorHandler {

    @AfterThrowing(value = "pointCutForAllMethodCalls()",throwing = "ex")
    public void exceptionHandler(JoinPoint joinPoint,Exception ex){
       log.error("Exception Occurred");
       log.error("Class: {}",joinPoint.getTarget().getClass().getSimpleName());
       log.error("Method: {}",joinPoint.getSignature().getName());

       log.error("Message: {}",ex.getMessage());
       log.error("Exception type: {}",ex.getClass().getSimpleName());
    }


}
