package com.support.service;

import com.support.pojo.community;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @InterfaceName communityService
 * @Author 吴俊淇
 * @Date 2020/3/26 0:17
 * @Version 1.0
 **/
public interface communityService {

    community findByEmail(String email);

    @Transactional
    int updateByEmailAndPassword(String password,String email);

    community save(community community);

    List<community> findAll();

    List<Object> findcommunityRelateElderly();//社区关联老人

    Optional<community> findById(Integer id);
}
