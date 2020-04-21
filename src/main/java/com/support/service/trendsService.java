package com.support.service;

import com.support.pojo.trends;

import java.util.List;

/**
 * @InterfaceName trendsService
 * @Author 吴俊淇
 * @Date 2020/3/28 0:26
 * @Version 1.0
 **/
public interface trendsService {
    trends save(trends trends);

    List<trends> findByUserIdOrderByDateDesc(Integer userId);

    int deleteByUserIdAndTrendsId(Integer userId,Integer trendsId);

    List<trends> findAllByDateDesc();
}
