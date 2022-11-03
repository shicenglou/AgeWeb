//package com.age.security.service.impl;
//
//import com.age.security.entity.SysUser;
//import com.age.security.model.LoginUser;
//import com.age.security.model.MiniUser;
//import com.age.security.service.LoginUserService;
//import com.age.security.service.SysUserService;
//import com.age.security.utils.JwtUtil;
//import com.age.servicebase.model.Result;
//import com.age.servicebase.utils.RedisUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//
//@Slf4j
//@Service
//public class LoginUserServiceImpl implements LoginUserService {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Override
//    public Result login(SysUser user) {
//        log.info("已经进入login接口");
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
//        log.info("用户名密码导入到了，UsernamePasswordAuthenticationToken");
//        //显然，该方法会进行校验
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        log.info("取完authenticate后");
//        log.info("authenticate:{}",authenticate);
//        if (authenticate == null){
//            return Result.error("用户名或密码错误");
//        }
//        //根据userid生成token
//        LoginUser loginUser  = (LoginUser) authenticate.getPrincipal();
//        String userId = loginUser.getSysUser().getId().toString();
//        String jwt = JwtUtil.createJwt(userId, 3000000L);
//        if (redisUtil.hasKey("login:"+userId)){
//            redisUtil.del("login:"+userId);
//        }
//        MiniUser miniUser = new MiniUser();
//        miniUser.setId(userId);
//        miniUser.setUserName(user.getUserName());
//        miniUser.setPassword(user.getPassword());
//        miniUser.setPhone(user.getPhonenumber());
//        redisUtil.set("login:"+userId,miniUser,3000L);
//        HashMap<String,String> map = new HashMap<>();
//        map.put("token",jwt);
//        return Result.ok(map);
//    }
//
//    @Override
//    public Result logout() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//        String userid = loginUser.getSysUser().getId();
//        redisUtil.del("login:"+userid);
//        return Result.ok("登出成功");
//    }
//
//    /**
//     * 注册
//     * @param user      用户注册基本信息
//     * @return          注册信息返回
//     */
//    @Override
//    public Result register(SysUser user) {
//        //验证码通过之后
//        SysUser one = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, user.getUserName()));
//        if (one != null){
//            return Result.error("用户名已被占用，请重新命名");
//        }
//        SysUser one1 = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, user.getPhonenumber()));
//        if (one1 != null){
//            return Result.error("手机号已经注册");
//        }
//        //进行密码加密
//        BCryptPasswordEncoder encoderb = new BCryptPasswordEncoder(16);
//        String encode = encoderb.encode("{bcrypt}"+user.getPassword());
//        user.setPassword(encode);
//        boolean save = sysUserService.save(user);
//        if (save){
//            return Result.ok("注册成功");
//        }
//        return Result.error("注册失败");
//    }
//}
