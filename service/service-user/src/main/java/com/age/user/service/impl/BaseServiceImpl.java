package com.age.user.service.impl;

import com.age.user.entity.Base;
import com.age.user.entity.Info;
import com.age.user.mapper.UserBaseMapper;
import com.age.user.service.BaseService;
import com.age.user.service.InfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
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
 * @since 2022-10-11
 */
@Service
@Slf4j
public class BaseServiceImpl extends ServiceImpl<UserBaseMapper, Base> implements BaseService {

    @Autowired
    private InfoService infoService;


    @Override
    public String register(Base base) {

        if (StringUtils.isBlank(base.getPassword())){
            return "密码不能为空";
        }
        if (StringUtils.isBlank(base.getAccount())){
            return "账号不能为空";
        }

        Base phoneCheck = this.getOne(new LambdaQueryWrapper<Base>().eq(Base::getPhone, base.getPhone()));
        if (phoneCheck != null){
            return "手机号不可重复注册";
        }
        Base accountCheck = this.getOne(new LambdaQueryWrapper<Base>().eq(Base::getAccount, base.getAccount()));
        if (accountCheck != null){
            return "账号不可重复注册";
        }
        base.setId(UUID.randomUUID().toString().replace("-",""));
        Info info = new Info();
        info.setId(UUID.randomUUID().toString().replace("-",""));
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());
        info.setPhoneHistory(base.getPhone());
        info.setUserId(base.getId());
        boolean save = infoService.save(info);
        if (!save){
            return "注册失败";
        }
        boolean save1 = this.save(base);
        if (!save1){
            return "注册失败";
        }
        log.info("新用户注册成功 {} 账号:{},手机号:{},",base.getName(),base.getAccount(),base.getPhone());
        return null;
    }
}
