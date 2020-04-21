package com.support.service;

import com.support.pojo.communityRelation;

import java.util.List;

/**
 * @InterfaceName communityRelationService
 * @Author 吴俊淇
 * @Date 2020/3/26 14:25
 * @Version 1.0
 **/
public interface communityRelationService {
    communityRelation findBycommunityIdAndUserId(Integer communityId, Integer userId);

    communityRelation save(communityRelation communityRelation);

    communityRelation findByUserId(Integer userId);

    void deleteByUserId(Integer userId);


}
