package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//this annotation is used for method base
@Retention(RetentionPolicy.RUNTIME)//it's only active on run time
public @interface DefaultExceptionMessage {

    String defaultMessage() default "";
}
