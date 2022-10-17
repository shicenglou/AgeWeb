package com.age.security.service.impl;

import com.age.security.entity.SysUser;
import com.age.security.model.LoginUser;
import com.age.security.service.LoginUserService;
import com.age.security.utils.JwtUtil;
import com.age.servicebase.model.Result;
import com.age.servicebase.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class LoginUserServiceImpl implements LoginUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result login(SysUser user) {
        log.info("已经进入login接口");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (authenticate == null){
            return Result.error("用户名或密码错误");
        }
        //根据userid生成token
        LoginUser loginUser  = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSysUser().getId().toString();
        String jwt = JwtUtil.createJwt(userId, 30000L);
        redisUtil.set("login:"+userId,loginUser,30000L);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return Result.ok(map);
    }

    @Override
    public Result logout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getSysUser().getId();
        redisUtil.del("login:"+userid);
        return Result.ok("登出成功");
    }
}
