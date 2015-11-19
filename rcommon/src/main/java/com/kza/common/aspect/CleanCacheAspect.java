package com.kza.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by kza.kong on 2015/11/19.
 */
@Component
@Aspect
public class CleanCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(CleanCacheAspect.class);

    @Around("execution(* *(..)) && @annotation(com.kza.common.annotations.CleanCached)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        return null;
    }
}
