package com.example.spring_boot;

import com.example.spring_boot.annotation.MyLog;
import com.example.spring_boot.annotation.MyTag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/toHello")
public class HelloController {

    @MyLog
    @RequestMapping("/hello")
    public void hello() {
        System.out.println("1111111111111111");
    }

    @GetMapping(value="/testsMyTag")
    @MyTag(age=39)
    public String testMyTag(HttpServletRequest request, HttpServletResponse response){
        String tip = (String) request.getAttribute("tip");
        return tip;
    }
}
