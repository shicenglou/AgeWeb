package com.age.user.controller;

import com.age.servicebase.model.Result;
import com.age.servicebase.utils.RedisUtil;
import com.age.user.entity.Base;
import com.age.user.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@Slf4j
@Api(tags = "登陆业务")
@RequestMapping("/user/sso")
public class LoginController {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BaseService baseService;

    @ApiOperation(value = "获取验证码")
    @GetMapping("/getCode")
    public Result getCode(@RequestParam String number){
        //验证码
        Random random = new Random();
        IntStream ints = random.ints(6, 0, 10);

        final int[] nums = new int[1];
        nums[0] = 0;
        ints.forEach(item->{
            nums[0] = nums[0]*10 +item;
        });
        log.info("number = {},count = {}",number,nums[0]);
        String key = "verify::code::"+number;
        String o = (String) redisUtil.get(key);
        if (StringUtils.isNotBlank(o)){
            redisUtil.del(key);
        }
        boolean hset = redisUtil.set(key, String.valueOf(nums[0]), 300);
        if (!hset){return Result.error("验证码获取失败！");}
        return Result.ok(nums[0]);
    }

    @GetMapping("/register")
    public Result register(@RequestBody Base base){
        System.out.println(base);
        String code = (String) redisUtil.get("verify::code::"+base.getPhone());
        if (StringUtils.isBlank(code)){
            return Result.error("验证码已失效，请重新验证");
        }
        if (!code.equals(base.getCode())){
            return Result.error("验证码错误");
        }
        redisUtil.del("verify::code::"+base.getPhone());
        String mesg = baseService.register(base);
        if (StringUtils.isBlank(mesg)){
            return Result.ok("注册成功");
        }
        return Result.error(mesg);
    }
}
