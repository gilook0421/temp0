package com.example.kgw.temp0;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class HelloController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World";
    }

}