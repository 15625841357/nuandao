package com.support.repository;

import com.support.pojo.friendToken;
import com.support.pojo.userRelation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @InterfaceName userRelationRepository
 * @Author 吴俊淇
 * @Date 2020/3/24 0:12
 * @Version 1.0
 **/
public interface userRelationRepository extends CrudRepository<userRelation, Integer> {
    //有多个怎么办
//    @Modifying
//    @Query(value = "update friend_token set b_follow=:#{#f.bFollow} where concern=:#{#f.concern} and concerned=:#{#f.concerned}", nativeQuery = true)
//    List<userRelation> findByConcern(Integer concern);

    List<userRelation> findByConcern(Integer concern);

    List<userRelation> findByConcerned(Integer concerned);

    userRelation findByConcernAndConcerned(Integer concern, Integer concerned);

    int deleteByConcernAndConcerned(Integer concern, Integer concerned);
}
