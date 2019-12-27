package com.example.spring_boot;

import com.example.spring_boot.annotation.MyLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toHello")
public class HelloController {

    @MyLog
    @RequestMapping("/hello")
    public void hello() {
        System.out.println("1111111111111111");
    }
}
