package com.support.task;

import com.support.pojo.WeChatMessage;
import com.support.pojo.userRelation;
import com.support.repository.userRelationRepository;
import com.support.service.userRelationService;
import com.support.service.userService;
import com.support.utils.TimeConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName task
 * @Author 吴俊淇
 * @Date 2020/4/7 14:35
 * @Version 1.0
 **/
//@Component
@Slf4j
public class task {
    @Autowired
    private static userService userService;
    @Autowired
    private static userRelationService userRelationService;

    /**
     * 微信推送
     */
//    @Scheduled(cron = "0 0 23 * * ?")
    public static void tuiSong(Integer id) {
        String time = TimeConversionUtil.getTimeStrNowByInstant();
        Map<String, Object> data = new HashMap<>();
        WeChatMessage weChatMessage = new WeChatMessage();
        System.out.println(id);
        List<userRelation> userRelations = userRelationService.findByConcern(id);
        System.out.println(userRelations);
//        user u = userService.findById(id).get();
//        userRelations.forEach(i -> {
//            user user = userService.findById(i.getConcerned()).get();
//            data.put("thing1", "我是文权的爸爸");
//            data.put("date2", time);
//            data.put("name3", u.getName());
//            weChatMessage.setOpenId(user.getOpenId());
//            weChatMessage.setTemplateId("ceHT9A7PemdGKavySKZySYHeiAdW8KgSox32Xqh9oOY");
//            weChatMessage.setPage("pages/openMap/openMap?name=" + u.getName());
//            weChatMessage.setData(data);
//            String s = WeChatUtils.push(weChatMessage);
//            log.info(s);
//        });
    }
}
