package com.support.service.Impl;

import com.support.pojo.community;
import com.support.service.communityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName communityServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/26 0:19
 * @Version 1.0
 **/
@Service
//@CacheConfig(cacheNames = "community:user")
public class communityServiceImpl implements communityService {
    @Autowired
    private com.support.repository.communityRepository communityRepository;

    //    @Cacheable(key = "'user_findByEmail'")
    @Override
    public community findByEmail(String email) {
        communityRepository.findById(1);
        return communityRepository.findByEmail(email);
    }

    //    @CacheEvict(value = "community:user", allEntries = true)
    @Override
    public int updateByEmailAndPassword(String password, String email) {
        return communityRepository.updateByEmailAndPassword(password, email);
    }

    //    @CacheEvict(value = "community:user", allEntries = true)
    @Override
    public community save(community community) {
        return communityRepository.save(community);
    }

    //    @Cacheable(key = "'user_findAll'")
    @Override
    public List<community> findAll() {
        return (List<community>) communityRepository.findAll();
    }

    @Override
    public List<Object> findcommunityRelateElderly() {
        return communityRepository.findcommunityRelateElderly();
    }

    @Override
    public Optional<community> findById(Integer id) {
        return communityRepository.findById(id);
    }

}
