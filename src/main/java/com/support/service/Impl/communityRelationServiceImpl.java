package com.support.service.Impl;

import com.support.pojo.communityRelation;
import com.support.repository.communityRelationRepository;
import com.support.service.communityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName communityRelationServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/26 14:25
 * @Version 1.0
 **/
@Service
public class communityRelationServiceImpl implements communityRelationService {
    @Autowired
    private communityRelationRepository communityRelationRepository;

    @Override
    public communityRelation findBycommunityIdAndUserId(Integer communityId, Integer userId) {
        return communityRelationRepository.findBycommunityIdAndUserId(communityId, userId);
    }

    @Override
    public communityRelation save(communityRelation communityRelation) {
        return communityRelationRepository.save(communityRelation);
    }

    @Override
    public communityRelation findByUserId(Integer userId) {
        return communityRelationRepository.findByUserId(userId);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        communityRelationRepository.deleteById(userId);
    }
}
