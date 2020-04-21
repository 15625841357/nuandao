package com.support.service.Impl;

import com.support.pojo.trends;
import com.support.repository.trendsRepository;
import com.support.service.trendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName trendsServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/28 0:27
 * @Version 1.0
 **/
@Service
public class trendsServiceImpl implements trendsService {
    @Autowired
    private trendsRepository trendsRepository;

    @Override
    public trends save(trends trends) {
        return trendsRepository.save(trends);
    }

    @Override
    public List<trends> findByUserIdOrderByDateDesc(Integer userId) {
        return trendsRepository.findByUserIdOrderByDateDesc(userId);
    }

    @Override
    public int deleteByUserIdAndTrendsId(Integer userId, Integer trendsId) {
        return trendsRepository.deleteByUserIdAndTrendsId(userId, trendsId);
    }

    @Override
    public List<trends> findAllByDateDesc() {
        return trendsRepository.findAllByDateDesc();
    }


}
