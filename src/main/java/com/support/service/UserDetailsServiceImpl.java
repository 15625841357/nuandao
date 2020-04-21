package com.support.service;

import com.support.pojo.JwtUser;
import com.support.pojo.community;
import com.support.repository.communityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserDetailsServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/19 14:30
 * @Version 1.0
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private communityRepository communityRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        community community = communityRepository.findByEmail(s);
        return new JwtUser(community);
    }

}
