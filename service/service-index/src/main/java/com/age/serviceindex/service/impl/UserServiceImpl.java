package com.age.serviceindex.service.impl;

import com.age.serviceindex.entity.User;
import com.age.serviceindex.mapper.UserMapper;
import com.age.serviceindex.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dudubired
 * @since 2022-09-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public String register(User user) {
        System.out.println(user);
        if (StringUtils.isBlank(user.getName())){
            return "用户名不可为空";
        }
        User one = this.getOne(new LambdaQueryWrapper<User>()
                .eq(StringUtils.isNotBlank(user.getEmail()), User::getEmail, user.getEmail())
                .eq(user.getPhone() != null, User::getPhone, user.getPhone()));
        if (one != null){
            return "手机号与邮箱不可重复使用";
        }
        user.setId(UUID.randomUUID().toString().replace("-",""));
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        this.saveOrUpdate(user);
        return null;
    }
}
