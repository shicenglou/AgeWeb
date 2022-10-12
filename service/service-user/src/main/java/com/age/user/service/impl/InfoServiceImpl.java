package com.age.user.service.impl;

import com.age.user.entity.Info;
import com.age.user.mapper.InfoMapper;
import com.age.user.service.InfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dudubired
 * @since 2022-10-11
 */
@Service
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements InfoService {

}
