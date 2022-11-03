package com.age.security.filter;

import com.age.security.utils.JwtUtil;
import com.age.servicebase.config.RedisConfig;
import com.age.servicebase.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class TokenAutoFilter extends BasicAuthenticationFilter {

    private RedisUtil redisUtil;

    private static final String roleHead = "ROLE_";

    public TokenAutoFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil){
        super(authenticationManager);
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        log.info("获取当前认证成功用户权限信息");
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        //判断如果有权限信息，放到权限上下文中
        if(authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
            log.info("授权成功");
        }
        chain.doFilter(request,response);
    }


    //从请求中，提取认证信息
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        //从header获取token
        String token = request.getHeader("token");
        if(token != null) {
            //从token获取用户名
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

            String username = JwtUtil.getUserInfoFromToken(token);
            //从redis获取对应权限列表
            List<String> permissionValueList = (List<String>)redisUtil.get(username);
            Collection<GrantedAuthority> authority = new ArrayList<>();
            for(String permissionValue : permissionValueList) {
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permissionValue);
                authority.add(auth);
            }
            //将权限信息加入到认证中
            return new UsernamePasswordAuthenticationToken(username,token,authority);
        }
        return null;
    }


}
