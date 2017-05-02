package com.spider.common.annotation;

import java.lang.annotation.*;

/**
 * Created by jian.Michael on 2017/3/30.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Usage {

    String value() default "";

}
