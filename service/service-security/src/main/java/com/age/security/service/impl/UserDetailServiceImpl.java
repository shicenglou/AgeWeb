package com.age.security.service.impl;

import com.age.security.entity.SysUser;
import com.age.security.mapper.SysUserMapper;
import com.age.security.model.LoginUser;
import com.age.security.model.MiniUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("进入userDetail验证token");
        log.info(String.valueOf(new Date()));
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, s));
        log.info(String.valueOf(new Date()));
        if (sysUser == null){
            throw new RuntimeException("用户名或密码错误");
        }
        MiniUser miniUser = new MiniUser();
        miniUser.setPhone(sysUser.getPhonenumber());
        miniUser.setUserName(sysUser.getUserName());
        miniUser.setPassword(sysUser.getPassword());
        miniUser.setId(String.valueOf(sysUser.getId()));
        return new LoginUser(miniUser);
    }
}
