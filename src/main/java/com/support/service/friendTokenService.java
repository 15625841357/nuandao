package com.support.service;

import com.support.pojo.friendToken;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @InterfaceName friendTokenService
 * @Author 吴俊淇
 * @Date 2020/3/26 21:39
 * @Version 1.0
 **/

public interface friendTokenService {

    List<friendToken> findByConcerned(Integer concerned);

    int updateByConcernAndConcerned(@Param("f") friendToken f);

    friendToken save(friendToken friendToken);

    friendToken findByConcernAndConcerned(Integer concern, Integer concerned);

    Integer deleteByConcernAndConcerned(Integer concern,Integer concerned);
}
