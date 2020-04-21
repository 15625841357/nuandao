package com.support.mapper;

import com.support.pojo.view.community_user;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName community_userMapper
 * @Author 吴俊淇
 * @Date 2020/3/27 11:10
 * @Version 1.0
 **/
@Component
public interface community_userMapper {
    List<community_user>findAllByCommunity_id(Integer id);
}
