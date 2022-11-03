//package com.age.security.filter;
//
//
//import com.age.security.entity.SysUser;
//import com.age.security.model.LoginUser;
//import com.age.security.model.MiniUser;
//import com.age.security.utils.JwtUtil;
//import com.age.servicebase.utils.RedisUtil;
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//
//@Slf4j
//@Component
//public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    //这个过滤器会去获取请求头中的token，对token进行解析取出其中的userid
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String token = request.getHeader("token");
//
//        log.info("进入了token过滤器");
//        if (StringUtils.isBlank(token)){
//            //放行
//            chain.doFilter(request, response);
//            log.info("放行");
//            return;
//        }
//        //解析token
//        String userid;
//        try {
//            Claims claims = JwtUtil.parseJWT(token);
//            userid = claims.getSubject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("token非法");
//        }
//        String redisKey = "login:" + userid;
//        HashMap<String,Object> result = (HashMap<String, Object>) redisUtil.get(redisKey);
//        MiniUser miniUser = new MiniUser();
//        miniUser.setId(((String) result.get("id")));
//        miniUser.setUsername((String) result.get("username"));
//        miniUser.setPassword((String) result.get("password"));
//        miniUser.setPhone((String) result.get("phone"));
//        LoginUser user = new LoginUser(miniUser);
//
//
//        //存入SecurityContextHolder
//        //TODO 获取权限信息封装到Authentication中
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(user,null,null);
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(authenticationToken);
//        //放行
//        chain.doFilter(request, response);
//    }
//}
