package com.age.security.service;

import com.age.security.entity.SysUser;
import com.age.servicebase.model.Result;

public interface LoginUserService {
    Result login(SysUser user);

    Result logout();
}
