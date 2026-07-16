package com.nithin.aopImplementation.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {
    @Before("pointCutForAllMethodCalls()")
    public void loggingBeforeHandler(JoinPoint joinPoint){
        log.info("Before {} call ",joinPoint.getSignature().getName());
    }

    @After("pointCutForAllMethodCalls()")
    public void loggingAfterHandler(JoinPoint joinPoint){
        log.info("After {} call ",joinPoint.getSignature().getName());
    }


    @Pointcut("execution(* com.nithin.aopImplementation.service.impl.*.*(..)))")
    public void pointCutForAllMethodCalls(){}
}
