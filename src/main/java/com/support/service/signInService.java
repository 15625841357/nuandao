package com.support.service;

import com.support.pojo.signIn;
import com.support.repository.signInRepository;
import org.springframework.data.repository.query.Param;

/**
 * @InterfaceName signInService
 * @Author 吴俊淇
 * @Date 2020/3/27 14:53
 * @Version 1.0
 **/
public interface signInService {
    signIn save(signIn signIn);

    signIn findByUserId(Integer userId);

    int updateTextByuserId(@Param("s") signIn s);
}
