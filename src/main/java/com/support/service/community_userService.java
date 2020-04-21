package com.support.service;

import com.support.pojo.view.community_user;

import java.util.List;

/**
 * @InterfaceName community_userService
 * @Author 吴俊淇
 * @Date 2020/3/26 23:19
 * @Version 1.0
 **/

/**
 * 社区用户联系
 */
public interface community_userService {
    List<community_user> findAllByCommunity_id(Integer id);
}
