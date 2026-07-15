package com.nithin.aopApp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {


//    @Before("execution(* orderPackage(..))")
    @Before("execution(* com.nithin.aopApp.service.impl.ShipmentServiceImpl.*(..))")
    public void beforeOrderPackageCall(JoinPoint joinPoint){
        log.info("Before order package call {}", joinPoint.getKind());
        log.info("Before order package call {}", joinPoint.getSignature());
        log.info("Before order package call {}", joinPoint.getSourceLocation());
    }

    @Before("within(com.nithin.aopApp..*)")
    public void beforeShipmentServiceCalls(JoinPoint joinPoint){
        log.info("Before All calls {}",joinPoint.getSignature());
    }


    @After("myLoggingAnnotationLog()")
    public void afterAnnotatedMethodLogs(){
        log.info("After method with my logging annotation");
    }

    @Before("@annotation(com.nithin.aopApp.aspect.MyLogging)")
    public void beforeAnnotatedMethodCalls(JoinPoint joinPoint){
        log.info("Annotated with my logging{}",joinPoint.getSignature());
    }


    @Pointcut("@annotation(com.nithin.aopApp.aspect.MyLogging) && within(com.nithin.aopApp..*)")
    public void myLoggingAnnotationLog(){}
}
