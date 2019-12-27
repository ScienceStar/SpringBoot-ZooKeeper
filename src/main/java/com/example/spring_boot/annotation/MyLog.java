package com.example.spring_boot.annotation;

import java.lang.annotation.*;

/**
 * 记录日志注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyLog {
}
