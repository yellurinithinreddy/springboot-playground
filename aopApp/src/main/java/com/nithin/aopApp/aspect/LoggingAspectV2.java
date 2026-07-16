package com.nithin.aopApp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspectV2 {

    @Before("pointCutForServiceMethodCalls()")
    public void beforeServiceMethodCall(JoinPoint joinPoint){
        log.info("Before Method calls {}",joinPoint.getSignature());
    }

//    @After("pointCutForServiceMethodCalls()")
    @AfterReturning(value = "pointCutForServiceMethodCalls()",returning = "returnedObject")
    public void afterServiceMethodCall(JoinPoint joinPoint, Object returnedObject){
        log.info("after returning Method calls {}, {}",joinPoint.getSignature(), returnedObject);
    }

    @AfterThrowing("pointCutForServiceMethodCalls()")
    public void afterThrowingExceptionMethodCall(JoinPoint joinPoint){
        log.info("after throwing Method calls {}",joinPoint.getSignature());
    }


    @Around("pointCutForServiceMethodCalls()")
    public Object logMethodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object returnedObject = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("execution time of a method is: {}, {}",(end-start),proceedingJoinPoint.getSignature());
        return returnedObject;
    }

    @Pointcut("execution(* com.nithin.aopApp.service.impl.*.*(..))")
    public void pointCutForServiceMethodCalls(){}
}
