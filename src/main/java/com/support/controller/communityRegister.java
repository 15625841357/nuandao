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
import com.support.utils.MailUtil;
import com.support.utils.RedisUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.support.service.communityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Random;

/**
 * @ClassName communityRegister
 * @Author 吴俊淇
 * @Date 2020/3/30 17:19
 * @Version 1.0
 **/
@RestController
public class communityRegister {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private communityService communityService;
    @Resource
    private RedisUtil redisUtil;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    @PostMapping("/sendcode")
    @ApiOperation(value = "发送验证码", notes = "发送验证码")
    public String sendcode(@RequestParam("email") String email) throws UnsupportedEncodingException, GeneralSecurityException, MessagingException {
        String body = randomChar();
        redisUtil.set("code:" + email, body, 60 * 5);
        MailUtil.SendMail(email, "验证码", "【验证码】:" + body + "\t5分钟后失效.");
        return "1";
    }

    @PostMapping("/register")
    @ApiOperation(value = "社区注册", notes = "社区注册")
    public String register(community community, String code) {
        String securityCode = String.valueOf(redisUtil.get("code:" + community.getEmail()));
        if (StringUtils.isEmpty(securityCode)) {
            return gson.toJson(ImmutableMap.of("result", "fail", "info", "没有发送验证码或者验证码过期"));
        }
        if (code.equals(securityCode)) {
            if (StringUtils.isEmpty(communityService.findByEmail(community.getEmail()))) {
                community.setPassword(bCryptPasswordEncoder.encode(community.getPassword()));
                community.setRole("ROLE_ADMIN");
                communityService.save(community);
                redisUtil.del("code:" + community.getEmail());
                return gson.toJson(ImmutableMap.of("result", "success", "info", "注册成功"));
            } else {
                return gson.toJson(ImmutableMap.of("result", "fail", "info", "该邮箱已被注册"));
            }
        } else {
            return gson.toJson(ImmutableMap.of("result", "fail", "info", "验证码错误"));
        }
    }


    private String randomChar() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder uuid = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            uuid.append(ch);
        }
        return uuid.toString();
    }

    @PostMapping("/test")
    public String test() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(communityService.findByEmail("123"));
    }


}
