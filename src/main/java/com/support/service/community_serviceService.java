package com.support.service;

import com.support.pojo.community_service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @InterfaceName community_serviceService
 * @Author 吴俊淇
 * @Date 2020/3/26 23:48
 * @Version 1.0
 **/
public interface community_serviceService {
    community_service save(community_service community_service);

    Optional<community_service> findById(Integer id);

    Integer findAllByUserIdAndStatus(Integer userId, Integer status);

    List<community_service> findAllByUserId(Integer userId);

    List<community_service> findAllPageByUserId(Integer userId, Integer number, Integer page);

    @Transactional
    Integer updateStatusByServiceId(Integer status, Integer community_service_id);

    Integer findByUserIdAndHuLi(Integer userId);

    Integer findByUserIdAndBaoJie(Integer userId);

    Integer findByUserIdAndWeiXiu(Integer userId);

    Integer findByUserIdAndPeiLiao(Integer userId);

    List<Map<String,Object>> findByCommunityIdAndStatus(Integer id, Integer status);
}
