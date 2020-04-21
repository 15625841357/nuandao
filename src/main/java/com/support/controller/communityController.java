package com.support.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.support.pojo.*;
import com.support.pojo.view.community_user;
import com.support.service.*;
import com.support.utils.GetEmailUtils;
import com.support.utils.JwtTokenUtils;
import com.support.utils.RedisUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName communityController
 * @Author 吴俊淇
 * @Date 2020/3/19 15:24
 * @Version 1.0
 **/
@RestController
@Transactional
@Slf4j
@RequestMapping("/admin")
public class communityController {
    @Autowired
    private community_applicationService community_applicationService;
    @Autowired
    private community_serviceService community_serviceService;
    @Autowired
    private userRelationService userRelationService;
    @Autowired
    private userService userService;
    @Autowired
    private community_userService community_userService;
    @Autowired
    private com.support.repository.communityRepository communityRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private communityService communityService;
    @Autowired
    private communityRelationService communityRelationService;
    @Resource
    private RedisUtil redisUtil;
    private Gson gson = new Gson();
    private Gson egson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public int changePassword(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, HttpServletRequest request) {
        String email = GetEmailUtils.GetEmail(request);
        String p = communityService.findByEmail(email).getPassword();
        if (bCryptPasswordEncoder.matches(oldPwd, p)) {
            communityService.updateByEmailAndPassword(bCryptPasswordEncoder.encode(newPwd), email);
            return 1;
        } else {
            return 0;
        }
    }

    @PostMapping("/communityInfo")
    @ApiOperation(value = "返回社区信息", notes = "返回社区信息")
    public String communityInfo(HttpServletRequest request) {//返回没带password和role的
        return egson.toJson(communityService.findByEmail(GetEmailUtils.GetEmail(request)));
    }


    @PostMapping("/communityAndUserId_Relation")
    @ApiOperation(value = "社区关联用户", notes = "社区关联用户---0代表关注列表中已存在该用户---1代表用户不存在---2代表申请发送成功")
    public int communityAndUserId_Relation(@RequestParam("userId") Integer userId, HttpServletRequest request) {
        if (userService.findById(userId).isPresent()) {//表示有这个用户
            return Optional.ofNullable(communityRelationService.findByUserId(userId))
                    .map(c -> 0).orElseGet(() -> {  //没有关联则关联老人
                        String email = GetEmailUtils.GetEmail(request);
                        Integer communityId = communityService.findByEmail(email).getId();
                        community_application c = community_applicationService.findByUserIdAndCommunityId(userId, communityId);
                        if (StringUtils.isEmpty(c)) {
                            redisUtil.set("communityRelation:communityRelation-" + communityId + "-" + userId, new communityRelation(userId, communityId));//写进缓存
                            community_applicationService.save(new community_application(userId, communityId, 1));
                        } else {//有值
                            Integer i = c.getBFollow();
                            if (i >= 1) {
                                i++;//代表第N次添加
                            }
                            community_applicationService.save(new community_application(userId, communityId, i));
                        }
                        return 2;
                    });
        } else {//false表示没这个用户
            return 1;
        }
    }

    @PostMapping("/unfollow")
    @ApiOperation(value = "社区取消关联用户", notes = "社区取消关联用户")
    public String unfollow(@RequestParam("userId") Integer userId) {
        if (StringUtils.isEmpty(communityRelationService.findByUserId(userId))) {
            return "fail";
        } else {
            communityRelationService.deleteByUserId(userId);
            return "success";
        }
    }

    @PostMapping("/concerned")
    @ApiOperation(value = "谁关注了他", notes = "谁关注了他")
    public String concerned(@RequestParam("userId") Integer userId) {
        List<userRelation> userRelations = userRelationService.findByConcerned(userId);
        List<Object> objects = new ArrayList<>();
        userRelations.forEach(i -> {
            Map<String, Object> map = ImmutableMap.of("relationName", i.getRelationName(), "user", userService.findByIdNoOpenidAndRoleAndSecretKey(i.getConcern()));
            objects.add(map);
        });
        return gson.toJson(objects);
    }

    @PostMapping("/concern")
    @ApiOperation(value = "他关注了谁", notes = "他关注了谁")
    public String concern(@RequestParam("userId") Integer userId) {
        List<userRelation> userRelations = userRelationService.findByConcern(userId);
        List<Object> objects = new ArrayList<>();
        userRelations.forEach(i -> {
            Map<String, Object> map = ImmutableMap.of("relationName", i.getRelationName(), "user", userService.findByIdNoOpenidAndRoleAndSecretKey(i.getConcerned()));
            objects.add(map);
        });
        return gson.toJson(objects);
    }


    /**
     * 用户清单，未完成------------------------------------
     *
     * @return
     */
    @PostMapping("/userlist")
    public String userlist(HttpServletRequest request) {
        String email = GetEmailUtils.GetEmail(request);
        Integer id = communityService.findByEmail(email).getId();
        log.info(String.valueOf(id));
        List<community_user> i = community_userService.findAllByCommunity_id(communityService.findByEmail(email).getId());
        String s = gson.toJson(i);
        log.info(String.valueOf(s));
        return s;
    }

    /**
     * @param id
     * @return 返回个人用户数据
     */
    @PostMapping("/userInfo")
    public String userInfo(Integer id) {
        return egson.toJson(userService.findById(id).get());
    }


    @PostMapping("/authorized")
    public String authorized(Integer id, HttpServletRequest request) {
        String email = GetEmailUtils.GetEmail(request);
        Integer i = communityService.findByEmail(email).getId();
        communityRelation c = communityRelationService.findBycommunityIdAndUserId(i, id);
        if (StringUtils.isEmpty(c)) {//null
            return "fail";
        } else {// have
            return "success";
        }
    }

    @PostMapping("/servicesList")
    @ApiOperation(value = "通过用户id来查看所有服务", notes = "通过用户id来查看所有服务")
    public String servicesList(@RequestParam("userId") Integer userId) {
        List<community_service> c = community_serviceService.findAllByUserId(userId);
        return gson.toJson(c);
    }

    @PostMapping("/servicesCountByUserIdAndStatus")
    @ApiOperation(value = "通过用户id和状态status来查看服务处于什么阶段", notes = "通过用户id和状态status来查看服务处于什么阶段")
    public String servicesCountByUserIdAndStatus(@RequestParam("userId") Integer userId, @RequestParam("status") Integer status) {
        return gson.toJson(community_serviceService.findAllByUserIdAndStatus(userId, status));
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "修改status通过社区id和服务id", notes = "修改status通过社区id和服务id---0代表未受理---1代表同意---2代表不同意---3代表已完成")
    public Integer changeStatus(@RequestParam("status") Integer status, @RequestParam("community_service_id") Integer community_service_id) {
        return community_serviceService.updateStatusByServiceId(status, community_service_id);
    }

    /**
     * save
     *
     * @param tokenHeader
     * @param response
     * @return 验证token
     * @throws IOException
     */
    @PostMapping("/token")
    public void token(@RequestBody String tokenHeader, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("tokenHeader:"+tokenHeader);
        System.out.println(request.getHeader(JwtTokenUtils.TOKEN_HEADER));

//        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
//        System.out.println(token);
//        boolean expiration = JwtTokenUtils.isExpiration(token);
//        if (expiration) {
//            response.getWriter().append("fail");
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        } else {
//            response.getWriter().append("success");
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//        response.getWriter().flush();
//        response.getWriter().close();
    }
}
