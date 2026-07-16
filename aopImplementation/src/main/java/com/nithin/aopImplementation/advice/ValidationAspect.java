package com.nithin.aopImplementation.advice;

import com.nithin.aopImplementation.dto.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ValidationAspect {


    @Before("pointCutForAllMethodCalls() && @annotation(com.nithin.aopImplementation.annotation.ValidateEntity)")
    public void validateEntity(JoinPoint joinPoint){
        Object obj = joinPoint.getArgs()[0];

        if(obj instanceof SignUpRequest){
            SignUpRequest req = (SignUpRequest) obj;
            if(req.name().length() < 4) throw new RuntimeException("Name Length is less than 4");
            if(req.email().length() < 10) throw new RuntimeException("Email Length is less than 10");
            if(req.password().length() == 10) log.info("password length is 10");
        }

    }

    @Pointcut("execution(* com.nithin.aopImplementation.service.AuthService.*(..))")
    public void pointCutForAllMethodCalls(){}
}
