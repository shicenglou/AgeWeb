package com.age.serviceindex.controller;


import com.age.servicebase.model.Result;
import com.age.serviceindex.entity.User;
import com.age.serviceindex.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dudubired
 * @since 2022-09-29
 */
@RestController
@RequestMapping("/index/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        String msg = userService.register(user);
        if (StringUtils.isNotBlank(msg)){
            return Result.error(msg);
        }
        return Result.ok("注册成功");
    }

}

