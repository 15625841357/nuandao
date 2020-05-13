package com.support.service.Impl;

import com.support.pojo.community_service;
import com.support.repository.community_serviceRepository;
import com.support.service.community_serviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName community_serviceServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/26 23:50
 * @Version 1.0
 **/
@Service
public class community_serviceServiceImpl implements community_serviceService {
    @Autowired
    private community_serviceRepository community_serviceRepository;

    @Override
    public community_service save(community_service community_service) {
        return community_serviceRepository.save(community_service);
    }

    @Override
    public Optional<community_service> findById(Integer id) {
        return community_serviceRepository.findById(id);

    }

    @Override
    public Integer findAllByUserIdAndStatus(Integer userId,Integer status) {
        return  community_serviceRepository.findAllByUserIdAndStatus(userId,status);
    }

    @Override
    public List<community_service> findAllByUserId(Integer userId) {
        return community_serviceRepository.findAllByUserId(userId);
    }

    @Override
    public List<community_service> findAllPageByUserId(Integer userId, Integer number, Integer page) {
        return community_serviceRepository.findAllPageByUserId(userId,number,page);
    }

    @Override
    public Integer updateStatusByServiceId(Integer status, Integer community_service_id) {
        return community_serviceRepository.updateStatusByServiceId(status,community_service_id);
    }

    @Override
    public Integer findByUserIdAndHuLi(Integer userId) {
        return community_serviceRepository.findByUserIdAndHuLi(userId);
    }

    @Override
    public Integer findByUserIdAndBaoJie(Integer userId) {
        return community_serviceRepository.findByUserIdAndBaoJie(userId);
    }

    @Override
    public Integer findByUserIdAndWeiXiu(Integer userId) {
        return community_serviceRepository.findByUserIdAndWeiXiu(userId);
    }

    @Override
    public Integer findByUserIdAndPeiLiao(Integer userId) {
        return community_serviceRepository.findByUserIdAndPeiLiao(userId);
    }

    @Override
    public List<Map<String, Object>> findByCommunityIdAndStatus(Integer id, Integer status) {
        return community_serviceRepository.findByCommunityIdAndStatus(id,status);
    }


}
