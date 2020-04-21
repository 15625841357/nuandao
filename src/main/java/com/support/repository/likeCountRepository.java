package com.support.repository;

import com.support.pojo.likeCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @InterfaceName likeCountRepository
 * @Author 吴俊淇
 * @Date 2020/3/27 17:20
 * @Version 1.0
 **/
public interface likeCountRepository extends CrudRepository<likeCount, Integer> {
    @Modifying
    @Query(value = "delete from like_count where user_id =?1",nativeQuery = true)
    void deleteByUserId(Integer userId);

    @Query(value = "SELECT count(*) FROM like_count l WHERE l.trends_id=?1",nativeQuery = true)
    Integer findCountByTrendsId(Integer trendsId);
}
