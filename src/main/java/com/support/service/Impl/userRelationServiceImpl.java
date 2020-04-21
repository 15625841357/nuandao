package com.support.service.Impl;

import com.support.pojo.userRelation;
import com.support.repository.userRelationRepository;
import com.support.service.userRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName userRelationServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/26 21:46
 * @Version 1.0
 **/
@Service
public class userRelationServiceImpl implements userRelationService {
    @Autowired
    private userRelationRepository userRelationRepository;

    @Override
    public List<userRelation> findByConcern(Integer concern) {
        return userRelationRepository.findByConcern(concern);
    }

    @Override
    public List<userRelation> findByConcerned(Integer concerned) {
        return userRelationRepository.findByConcerned(concerned);
    }

    @Override
    public userRelation findByConcernAndConcerned(Integer concern, Integer concerned) {
        return userRelationRepository.findByConcernAndConcerned(concern,concerned);
    }

    @Override
    public int deleteByConcernAndConcerned(Integer concern, Integer concerned) {
       return userRelationRepository.deleteByConcernAndConcerned(concern,concerned);
    }

    @Override
    public userRelation save(userRelation userRelation) {
        return userRelationRepository.save(userRelation);
    }
}
