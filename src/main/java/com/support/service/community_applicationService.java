package com.support.service;

import com.support.pojo.community_application;

import java.util.List;

/**
 * @InterfaceName community_applicationService
 * @Author 吴俊淇
 * @Date 2020/4/3 23:45
 * @Version 1.0
 **/
public interface community_applicationService {
    community_application save(community_application community_application);

    List<community_application> findByUserId(Integer userId);

    community_application findByUserIdAndCommunityId(Integer userId,Integer communityId);

    community_application updateBFollowByuserId(Integer bFollow,Integer userId);

    void deleteByUserIdAndCommunityId(Integer userId,Integer communityId);
}
