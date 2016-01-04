package com.kza.common.annotations;

import java.lang.annotation.*;

/**
 * Annotation used for indicating a back end task can be locked by operators
 * @author pinkdahlia
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Locker {
	/**
	 * Task name to show on back end server. Must be given.
	 */
	String taskName();
	
	/**
	 * Declares whether the task is locked by default.
	 * <p>Defaults to {@code true}.
	 */
	boolean lockedByDefault() default true;
}
