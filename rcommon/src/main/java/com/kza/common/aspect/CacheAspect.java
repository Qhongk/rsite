package com.kza.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by kza on 2015/9/18.
 */
@Component
@Aspect
public class CacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Around("execution(* *(..)) && @annotation(com.kza.common.annotations.Cached)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return null;
    }
}
