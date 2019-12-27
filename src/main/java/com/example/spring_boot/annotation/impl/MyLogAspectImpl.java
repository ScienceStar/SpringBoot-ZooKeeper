package com.example.spring_boot.annotation.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * fun:注解类的实现
 */
@Component
@Aspect
public class MyLogAspectImpl {

    @Pointcut("@annotation(com.example.spring_boot.annotation.MyLog)")
    private void cut() {
        System.out.println("3");
    }

    // 开始环绕
    @Around("cut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("1");
        try {
            joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("4");
    }

    @Before("cut()")
    public void before() {
        System.out.println("2");
    }

    @After("cut()")
    public void after() {
        System.out.println("5");
    }
}
