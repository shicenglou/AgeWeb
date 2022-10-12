package com.age.music.service.impl;

import com.age.music.entity.Comment;
import com.age.music.mapper.CommentMapper;
import com.age.music.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dudubired
 * @since 2022-10-08
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
