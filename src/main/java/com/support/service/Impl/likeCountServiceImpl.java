package com.support.service.Impl;

import com.support.pojo.likeCount;
import com.support.repository.likeCountRepository;
import com.support.service.likeCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName likeCountServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/27 17:20
 * @Version 1.0
 **/
@Service
public class likeCountServiceImpl implements likeCountService {
    @Autowired
    private likeCountRepository likeCountRepository;
    @Override
    public likeCount save(likeCount likeCount) {
        return likeCountRepository.save(likeCount);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        likeCountRepository.deleteByUserId(userId);
    }

    @Override
    public Integer findCountByTrendsId(Integer trendsId) {
        return likeCountRepository.findCountByTrendsId(trendsId);
    }
}
