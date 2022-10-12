package com.age.music.service.impl;

import com.age.music.entity.Song;
import com.age.music.mapper.SongMapper;
import com.age.music.service.SongService;
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
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

}
