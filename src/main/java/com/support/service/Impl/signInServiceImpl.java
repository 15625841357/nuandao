package com.support.service.Impl;

import com.support.pojo.signIn;
import com.support.repository.signInRepository;
import com.support.service.signInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName signInServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/27 14:53
 * @Version 1.0
 **/
@Service
public class signInServiceImpl implements signInService {
    @Autowired
    private signInRepository signInRepository;
    @Override
    public signIn save(signIn signIn) {
        return signInRepository.save(signIn);
    }

    @Override
    public signIn findByUserId(Integer userId) {
        return signInRepository.findByUserId(userId);
    }

    @Override
    public int updateTextByuserId(signIn s) {
        return signInRepository.updateTextByuserId(s);
    }
}
