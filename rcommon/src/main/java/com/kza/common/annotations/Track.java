package com.kza.common.annotations;

import java.lang.annotation.*;

/**
 * Created by kza on 2015/9/18.
 */
@Documented
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Track {

}
