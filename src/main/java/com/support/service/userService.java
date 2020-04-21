package com.support.service;

import com.support.pojo.user;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @InterfaceName userService
 * @Author 吴俊淇
 * @Date 2020/3/26 21:14
 * @Version 1.0
 **/
public interface userService {
    @Transactional
    int updateById(@Param("u") user u);

    user findByOpenId(String openid);

    List<user> findAll();

    user save(user user);

    Optional<user> findById(Integer id);

    Map<String, Object> findByIdNoOpenidAndRoleAndSecretKey(Integer userId);

    @Transactional
    int updatePhotoByUserId(String photo, Integer userId);

    @Transactional
    void updateLongitudeAndLatitudeByUserId(String longitude, String latitude, Integer userId);
}
