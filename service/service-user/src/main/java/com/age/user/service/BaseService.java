package com.age.user.service;

import com.age.user.entity.Base;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dudubired
 * @since 2022-10-11
 */
public interface BaseService extends IService<Base> {

    String register(Base base);
}
