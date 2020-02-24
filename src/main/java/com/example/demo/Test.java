package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
//    //获取配置文件  属性值@Value
//    @Value("${name}")
//    private String name;
//
//    @Value("${age}")
//    private String age;

    //@RequestMapping  设置接口路由名称
    //@PostMapping     设置post接口
    //@GetMapping      设置get接口
    @RequestMapping("/hello")
    @PostMapping
    @GetMapping
    public String hello(String data) {
        return "返回结果:"+data;
    }
}
