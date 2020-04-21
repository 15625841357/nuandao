package com.support.repository;

import com.support.pojo.friendToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @InterfaceName friendTokenRepository
 * @Author 吴俊淇
 * @Date 2020/3/24 20:39
 * @Version 1.0
 **/
public interface friendTokenRepository extends CrudRepository<friendToken, Integer> {
    List<friendToken> findByConcerned(Integer concerned);

    @Modifying
    @Query(value = "update friend_token set b_follow=:#{#f.bFollow} where concern=:#{#f.concern} and concerned=:#{#f.concerned}", nativeQuery = true)
    int updateByConcernAndConcerned(@Param("f") friendToken f);

    friendToken findByConcernAndConcerned(Integer concern, Integer concerned);

    Integer deleteByConcernAndConcerned(Integer concern,Integer concerned);
}
