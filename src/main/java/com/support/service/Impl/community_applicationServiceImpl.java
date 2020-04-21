package com.support.service.Impl;

import com.support.pojo.community_application;
import com.support.service.community_applicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName community_applicationServiceImpl
 * @Author 吴俊淇
 * @Date 2020/4/3 23:45
 * @Version 1.0
 **/
@Service
public class community_applicationServiceImpl implements community_applicationService {
    @Autowired
    private com.support.repository.community_applicationRepository community_applicationRepository;

    @Override
    public community_application save(community_application community_application) {
        return community_applicationRepository.save(community_application);
    }

    @Override
    public List<community_application> findByUserId(Integer userId) {
        return community_applicationRepository.findByUserId(userId);
    }

    @Override
    public community_application findByUserIdAndCommunityId(Integer userId, Integer communityId) {
        return community_applicationRepository.findByUserIdAndCommunityId(userId,communityId);
    }

    @Override
    public community_application updateBFollowByuserId(Integer bFollow, Integer userId) {
        return community_applicationRepository.updateBFollowByuserId(bFollow,userId);
    }

    @Override
    public void deleteByUserIdAndCommunityId(Integer userId, Integer communityId) {
        community_applicationRepository.deleteByUserIdAndCommunityId(userId,communityId);
    }


}
