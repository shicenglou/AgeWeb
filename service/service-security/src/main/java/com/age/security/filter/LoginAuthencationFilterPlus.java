package com.age.security.filter;

import com.age.security.model.LoginUser;
import com.age.security.model.MiniUser;
import com.age.security.utils.JwtUtil;
import com.age.servicebase.model.Result;
import com.age.servicebase.utils.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginAuthencationFilterPlus extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;
    private AuthenticationManager authenticationManager;

    public LoginAuthencationFilterPlus(AuthenticationManager authenticationManager,  RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/admin/acl/login","POST"));

    }

    //1 获取表单提交用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException{
        try {
            MiniUser user = new MiniUser();

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            user.setUsername(username);
            user.setPassword(password);
            log.info("用户信息:{}",user);

            //传入进行登录验证
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),
                    new ArrayList<>()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        LoginUser user = (LoginUser) authResult.getPrincipal();
        //获取登陆成功用户
        String token = JwtUtil.createJwt(user.getUsername(), null);
        //把用户名称和用户权限列表放到redis
        redisTemplate.opsForValue().set(user.getUsername(),user.getPermissionValueList());
        //返回token
        HashMap<String, String> map = new HashMap<>();
        map.put("token",token);
        log.info("token返回");
        ResponseUtil.out(response, Result.ok(map));

    }

    //3 认证失败调用的方法
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        ResponseUtil.out(response, Result.error("登录认证失败"));
    }
}
