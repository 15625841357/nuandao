package com.support.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.support.pojo.community;
import com.support.pojo.communityRelation;
import com.support.pojo.community_service;
import com.support.pojo.view.community_user;
import com.support.service.community_serviceService;
import com.support.service.community_userService;
import com.support.utils.GetEmailUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.support.service.communityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName communityRegister
 * @Author 吴俊淇
 * @Date 2020/3/30 17:19
 * @Version 1.0
 **/
@RestController
public class communityRegister {
    @Autowired
    private community_serviceService community_serviceService;
    @Autowired
    private community_userService community_userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private communityService communityService;
    private Gson gson = new Gson();

    /**
     * @param community
     * @return 注册
     */
    @PostMapping("/register")
    @ApiOperation(value = "社区注册", notes = "社区注册")
    public String registerUser(@RequestBody community community) {
        System.out.println(community);
        if (StringUtils.isEmpty(communityService.findByEmail(community.getEmail()))) {
            community.setPassword(bCryptPasswordEncoder.encode(community.getPassword()));
            community.setRole("ROLE_ADMIN");
            communityService.save(community);
            return gson.toJson(ImmutableMap.of("result", "success", "info", "注册成功"));
        } else {
            return gson.toJson(ImmutableMap.of("result", "fail", "info", "该邮箱已被注册"));
        }
    }


    @PostMapping("/test")
    public String test() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(communityService.findByEmail("123"));
    }


}
