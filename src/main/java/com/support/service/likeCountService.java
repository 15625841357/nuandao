package com.support.service;

import com.support.pojo.likeCount;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @InterfaceName likeCountService
 * @Author 吴俊淇
 * @Date 2020/3/27 17:20
 * @Version 1.0
 **/
public interface likeCountService {
    likeCount save(likeCount likeCount);

    @Transactional
    void deleteByUserId(Integer userId);

    Integer findCountByTrendsId(Integer trendsId);
}
