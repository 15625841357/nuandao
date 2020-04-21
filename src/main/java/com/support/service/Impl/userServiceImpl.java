package com.support.service.Impl;

import com.support.pojo.user;
import com.support.repository.userRepository;
import com.support.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName userServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/21 14:20
 * @Version 1.0
 **/
@Service
public class userServiceImpl implements userService {

    @Autowired
    private userRepository userRepository;

    @Override
    public int updateById(user u) {
        return userRepository.updateById(u);
    }

    @Override
    public user findByOpenId(String openid) {
        return userRepository.findByOpenId(openid);
    }

    @Override
    public user save(user user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<user> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Map<String, Object> findByIdNoOpenidAndRoleAndSecretKey(Integer userId) {
        return userRepository.findByIdNoOpenidAndRoleAndSecretKey(userId);
    }

    @Override
    public int updatePhotoByUserId(String photo, Integer userId) {
        return userRepository.updatePhotoByUserId(photo,userId);
    }

    @Override
    public void updateLongitudeAndLatitudeByUserId(String longitude, String latitude, Integer userId) {
         userRepository.updateLongitudeAndLatitudeByUserId(longitude,latitude,userId);
    }

    @Override
    public List<user> findAll() {
        return (List<user>) userRepository.findAll();
    }
}
