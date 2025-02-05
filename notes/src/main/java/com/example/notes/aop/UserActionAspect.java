package com.example.notes.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class UserActionAspect {

    @Pointcut("@annotation(com.example.notes.aop.TrackUserAction)")
    public void trackUserActionMethods() {}

    @AfterReturning(value = "trackUserActionMethods()", returning = "result")
    public void logUserAction(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("Метод вызван: {}", methodName);
        log.info("Аргументы: {}", Arrays.toString(methodArgs));
        log.info("Результат: {}", result);
    }
}
