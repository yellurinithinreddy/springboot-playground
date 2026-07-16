package com.nithin.aopImplementation.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class MethodExecutionPerformance {


    @Around("pointcutForAllServiceMethods() && @annotation(com.nithin.aopImplementation.annotation.MeasureExecutionTime)")
    public Object measureExecutionTimeOfServiceMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object returned = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("Execution time of method {} is {}",proceedingJoinPoint.getSignature().getName(),(end-start));
        return returned;
    }

    @Pointcut("execution(* com.nithin.aopImplementation.service.impl.*.*(..))")
    public void pointcutForAllServiceMethods(){
    }
}
