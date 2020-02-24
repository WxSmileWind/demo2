package com.example.demo.controller;


import com.example.demo.Response_Message.RespCode;
import com.example.demo.Response_Message.RespEntity;
import com.example.demo.pojo.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller                           //控制器
@Api(tags = "测试接口2")
@RestController
public class GlobalController {


    @ApiOperation(value = "测试 - 捕获/")
    @PostMapping
    @GetMapping
    @RequestMapping("/")
    //List返回
    public RespEntity gs() {
        return new RespEntity(RespCode.SUCCESS);
    }

    @ApiOperation(value = "测试 - 捕获/csrf")
    @PostMapping
    @GetMapping
    @RequestMapping("/csrf")
    //List返回
    public RespEntity gsa() {
        return new RespEntity(RespCode.SUCCESS);
    }
}
