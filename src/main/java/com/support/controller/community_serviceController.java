package com.support.controller;

import com.support.pojo.community_service;
import com.support.repository.community_serviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName community_serviceController
 * @Author 吴俊淇
 * @Date 2020/3/20 15:03
 * @Version 1.0
 **/
@RestController
@RequestMapping("/service")
public class community_serviceController {
    @Autowired
    private community_serviceRepository community_serviceRepository;

    @PutMapping("/{id}?{cid}")
    public int update(@PathVariable("id") Integer id, @PathVariable("cid") Integer cid) {
        System.out.println("id:" + id + ":cid:" + cid);
        community_service community_service = new community_service();
        community_service.setCommunity_service_id(1);
//        community_service.setCommunity_id(133);
        community_serviceRepository.save(community_service);
        return 41412;
    }
}
