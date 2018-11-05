package com.better.anno.bean;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME) //启动的时候被代理
public @interface TableImageJPGE {

    /**
     * 标题
     * @return
     */
    String[] total() default {};
}
