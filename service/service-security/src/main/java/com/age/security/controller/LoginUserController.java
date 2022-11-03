package com.age.security.controller;


import com.age.security.entity.SysUser;
//import com.age.security.service.LoginUserService;
import com.age.servicebase.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class LoginUserController {
//
//    @Autowired
//    private LoginUserService loginUserService;

//    @PostMapping("/login")
//    public Result login(@RequestBody SysUser user){
//
//        return loginUserService.login(user);
//    }
//
//    @GetMapping("/logout")
//    public Result logout(){
//        return loginUserService.logout();
//    }

    @GetMapping("/passwordEncode")
    public Result bcrypt(@RequestParam String password){
        log.info("进入了密码编译接口");
        BCryptPasswordEncoder encoderb = new BCryptPasswordEncoder(16);
        Pbkdf2PasswordEncoder encoderp = new Pbkdf2PasswordEncoder();
        SCryptPasswordEncoder encoders = new SCryptPasswordEncoder();
//        String a = encodera.encode(password);
        String b = encoderb.encode(password);
        String p = encoderp.encode(password);
        String s = encoders.encode(password);
        Map<String, String> result = new HashMap<>();
        result.put("BCryptPasswordEncoder",b);
//        result.put("Argon2PasswordEncoder",a);
        result.put("Pbkdf2PasswordEncoder",p);
        result.put("SCryptPasswordEncoder",s);
        return Result.ok(result);
    }

    @GetMapping("/decode")
    public Result passDecode(@RequestParam String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches("123456", password);
        return Result.ok(matches);
    }

//    @PostMapping("/register")
//    public Result register(@RequestBody SysUser user){
//        return loginUserService.register(user);
//    }

}
