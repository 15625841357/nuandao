package com.support.repository;

import com.support.pojo.view.community_user;
import org.springframework.data.repository.CrudRepository;

/**
 * @InterfaceName community_userRepository
 * @Author 吴俊淇
 * @Date 2020/3/26 23:14
 * @Version 1.0
 **/
public interface community_userRepository extends CrudRepository<community_user, Integer> {
}
