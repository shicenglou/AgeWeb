package com.age.security.config;

import com.age.security.utils.JwtUtil;
import com.age.servicebase.model.Result;
import com.age.servicebase.utils.RedisUtil;
import com.age.servicebase.utils.ResponseUtil;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutHandlerPlus implements LogoutHandler {

    private RedisUtil redisUtil;

    LogoutHandlerPlus(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //1 从header里面获取token
        //2 token不为空，移除token，从redis删除token
        String token = request.getHeader("token");
        if(token != null) {
            //移除

            //从token获取用户名
            String username = JwtUtil.getUserInfoFromToken(token);
            redisUtil.del(username);
        }

        //设置response
        ResponseUtil.out(response, Result.ok());

    }
}
