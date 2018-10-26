package com.better.anno.bean;

import java.lang.annotation.*;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/25 17:36
 * @Description: 自定义POI接口
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelProperty {

    int index();
    boolean notNull() default false;
    int cellType() default -1;
}
