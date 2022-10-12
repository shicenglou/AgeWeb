package com.age.user.controller;


import com.age.servicebase.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dudubired
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/user/base")
public class BaseController {

    @GetMapping("/get")
    public Result getss(){
        return Result.ok("我是答应西");
    }

}

