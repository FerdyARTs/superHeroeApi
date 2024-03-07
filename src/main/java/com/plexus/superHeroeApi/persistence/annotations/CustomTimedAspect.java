package com.plexus.superHeroeApi.persistence.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomTimedAspect {
    private static final Logger logger = LoggerFactory.getLogger(CustomTimedAspect.class);
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(customTimed)")
    public void customTimedPointcut(CustomTimed customTimed) {}

    @Before("customTimedPointcut(customTimed)")
    public void beforeMethodExecution(JoinPoint joinPoint, CustomTimed customTimed) {
        logger.info("Starting execution of method: {}", joinPoint.getSignature().toShortString());

        startTime.set(System.currentTimeMillis());
    }

    @After("customTimedPointcut(customTimed)")
    public void afterMethodExecution(JoinPoint joinPoint, CustomTimed customTimed) {
        logger.info("Finished execution of method: {}", joinPoint.getSignature().toShortString());
        long duration = System.currentTimeMillis() - startTime.get();
        logger.info("Method execution time: {} ms", duration);

        startTime.remove();
    }
}

