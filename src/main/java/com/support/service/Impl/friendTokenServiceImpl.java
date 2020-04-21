package com.support.service.Impl;

import com.support.pojo.friendToken;
import com.support.service.friendTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName friendTokenServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/26 21:40
 * @Version 1.0
 **/
@Service
public class friendTokenServiceImpl implements friendTokenService {
    @Autowired
    private com.support.repository.friendTokenRepository friendTokenRepository;
    @Override
    public List<friendToken> findByConcerned(Integer concerned) {
        return friendTokenRepository.findByConcerned(concerned);
    }

    @Override
    public int updateByConcernAndConcerned(friendToken f) {
        return friendTokenRepository.updateByConcernAndConcerned(f);
    }

    @Override
    public friendToken save(friendToken friendToken) {
        return friendTokenRepository.save(friendToken);
    }

    @Override
    public friendToken findByConcernAndConcerned(Integer concern, Integer concerned) {
        return friendTokenRepository.findByConcernAndConcerned(concern,concerned);
    }

    @Override
    public Integer deleteByConcernAndConcerned(Integer concern, Integer concerned) {
        return friendTokenRepository.deleteByConcernAndConcerned(concern,concerned);
    }


}
