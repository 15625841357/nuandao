package com.support.task;

import com.support.pojo.communityRelation;
import com.support.pojo.user;
import com.support.service.communityRelationService;
import com.support.service.communityService;
import com.support.service.userRelationService;
import com.support.service.userService;
import com.support.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName task
 * @Author 吴俊淇
 * @Date 2020/4/7 14:35
 * @Version 1.0
 **/
@Component
@Slf4j
@EnableScheduling
public class task {
    @Autowired
    private userService userService;
    @Autowired
    private userRelationService userRelationService;
    @Autowired
    private communityService communityService;
    @Autowired
    private communityRelationService communityRelationService;
    @Resource
    private RedisUtil redisUtil;

    @Scheduled(cron = "00 00 12 * * ?")
    public void longTimeNoLogin() {
        List<user> users = userService.findAll();
        users.forEach(user -> {
            if (!redisUtil.hasKey("longTimeNoLogin:" + user.getId())) {
                java.util.Date date = TimeConversionUtil.StringTransferToDate(String.valueOf(user.getLastLogin()), "yyyy-MM-dd HH:mm:ss");
                long time = AgeUtlis.longtime(date);
                if (time >= 3) {
                    redisUtil.set("longTimeNoLogin:" + user.getId(), 1, 60 * 60 * 24);
                    ExecutorService executor = Executors.newFixedThreadPool(1);
                    executor.execute(() -> {
                        try {
                            new WeChatUtils(userService, userRelationService).tuiSong(user.getId(), "已经连续" + time + "没有登录啦，请联系下");
                            String name = userService.findById(user.getId()).get().getName();
                            communityRelation c = communityRelationService.findByUserId(user.getId());
                            String email = communityService.findById(c.getCommunityId()).get().getEmail();
                            String body = name + "已经连续" + time + "没有登录啦，请联系下" + name + ".";
                            MailUtil.SendMail(email, name + "已经连续" + time + "没有登录", body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    executor.shutdown();
                }
            }
        });
    }
}
