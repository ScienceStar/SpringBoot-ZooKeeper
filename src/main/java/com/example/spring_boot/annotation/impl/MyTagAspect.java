package com.example.spring_boot.annotation.impl;

import com.example.spring_boot.annotation.MyTag;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class MyTagAspect {

    @Before(value = "@annotation( com.example.spring_boot.annotation.MyTag)")
    public void dofore(JoinPoint jp) {
        try {
            //通过获取MyTag注解
            Method proxyMethod = ((MethodSignature) jp.getSignature()).getMethod();
            Method targetMethod = jp.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
            MyTag myTag = targetMethod.getAnnotation(MyTag.class);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            //处理注解逻辑
            int age = myTag.age();
            String tip = "";
            if (age <= 35 && age > 0) {
                tip = "年纪小于30岁先做程序员吧";
            } else if (age > 35 && age < 45) {
                tip = "年纪在30至35之间可以回去考公务员了";
            } else {
                tip = "你的年纪不在我考虑范围之内";
            }
            request.setAttribute("tip", tip);
        } catch (Throwable e) {
            System.out.println("有异常啊");
        }
    }
}
