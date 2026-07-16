package com.nithin.aopImplementation.advice;

import com.nithin.aopImplementation.entity.User;
import com.nithin.aopImplementation.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityHandler {
    private final JwtUtil jwtUtil;
    @Around("pointCutForAllMethodCalls()")
    public Object securityForAllServiceMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jwtUtil.validateToken(user.getAccessToken());
        log.info("user Token is valid and can access the service method");
        return proceedingJoinPoint.proceed();
    }


    @Pointcut("execution(* com.nithin.aopImplementation.service.impl.*.*(..))")
    public void pointCutForAllMethodCalls(){}
}
