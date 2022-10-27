package com.age.security.controller;

import com.age.servicebase.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultExitController {
    @PostMapping("/login")
    public Result login(){
        System.out.println("asdasdasdasd");
        return Result.ok("奥斯卡大奖","芭比Q了嘛不是");
    }
}
