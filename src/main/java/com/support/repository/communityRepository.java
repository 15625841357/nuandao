package com.support.repository;

import com.support.pojo.community;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName UserRepository
 * @Author 吴俊淇
 * @Date 2020/3/19 13:56
 * @Version 1.0
 **/
public interface communityRepository extends CrudRepository<community, Integer> {
    community findByEmail(String email);

    //测试更新save
    @Modifying
    @Query(value = "update community_info set password=?1 where email=?2", nativeQuery = true)
    int updateByEmailAndPassword(String password,String email);

    @Query(value = "SELECT * FROM community_info ci join community_relation cr on ci.id=cr.community_id join user_info ui on cr.elderly_id=ui.id",nativeQuery = true)
    List<Object> findcommunityRelateElderly();//社区关联老人
}
