package com.support.repository;

import com.support.pojo.community_application;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @InterfaceName community_applicationRepository
 * @Author 吴俊淇
 * @Date 2020/4/3 23:14
 * @Version 1.0
 **/
public interface community_applicationRepository extends CrudRepository<community_application, Integer> {
    List<community_application> findByUserId(Integer userId);

    community_application findByUserIdAndCommunityId(Integer userId,Integer communityId);

    @Modifying
    @Query(value = "update community_application set b_follow=?1 where user_id=?2", nativeQuery = true)
    community_application updateBFollowByuserId(Integer bFollow,Integer userId);

    void deleteByUserIdAndCommunityId(Integer userId,Integer communityId);
}
