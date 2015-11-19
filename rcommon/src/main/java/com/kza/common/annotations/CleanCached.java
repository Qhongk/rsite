package com.kza.common.annotations;

import java.lang.annotation.*;

/**
 * Created by kza on 2015/9/10.
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CleanCached {

    CacheTimePolicy time() default CacheTimePolicy.DEFUALT;

    String cache() default "redis";

    String key();

    String type();
}
