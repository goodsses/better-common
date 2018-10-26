package com.better.anno.bean;

import java.lang.annotation.*;

/**
 * @author pch 651158394@qq.com
 * @version 2018/8/30 9:04
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelProperty {

    int index();
    boolean notNull() default false;
    int cellType() default -1;
}
