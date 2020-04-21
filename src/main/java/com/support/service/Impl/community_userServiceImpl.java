package com.support.service.Impl;

import com.support.mapper.community_userMapper;
import com.support.pojo.view.community_user;
import com.support.repository.community_userRepository;
import com.support.service.community_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName community_userServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/26 23:19
 * @Version 1.0
 **/
@Service
public class community_userServiceImpl implements community_userService {
    @Autowired
    private community_userRepository community_userRepository;
    @Autowired
    private community_userMapper community_userMapper;

    @Override
    public List<community_user> findAllByCommunity_id(Integer id) {
        return community_userMapper.findAllByCommunity_id(id);
    }
}
