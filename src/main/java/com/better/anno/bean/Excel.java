package com.better.anno.bean;

import java.lang.annotation.*;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/25 17:36
 * @Description: 自定义POI接口
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME) //启动的时候被代理
public @interface Excel {

    /**
     * 标题名称
     * @return
     */
    String[] header() default {};

    /**
     * sheet名称，默认值为sheet1
     * @return
     */
    String sheetName() default "sheet1";
}