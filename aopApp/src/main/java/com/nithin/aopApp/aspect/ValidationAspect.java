package com.nithin.aopApp.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    @Pointcut("execution(* com.nithin.aopApp.service.impl.*.*(..))")
    public void pointCutForAllServiceMethodCalls(){}


    @Around("pointCutForAllServiceMethodCalls()")
    public Object validateInput(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        Long orderId = (Long) args[0];

        if(orderId >= 0) return proceedingJoinPoint.proceed();
        return "Cannot have the orderId as negative";
    }
}
