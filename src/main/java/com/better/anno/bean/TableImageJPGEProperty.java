package com.better.anno.bean;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableImageJPGEProperty {

    int index();

    boolean notNull() default false;
}
