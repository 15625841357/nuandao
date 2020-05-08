package com.support.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.support.exception.UserException;
import com.support.pojo.*;
import com.support.service.userRelationService;
import com.support.service.userService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName WeChatUtils
 * @Author 吴俊淇
 * @Date 2020/3/22 18:39
 * @Version 1.0
 **/
@Slf4j
public class WeChatUtils {
    /**
     * @param code
     * @return 获取openid和sessionkey
     */
    private static com.support.service.userService userService;
    private static com.support.service.userRelationService userRelationService;

//    @Autowired
//    public WeChatUtils(com.support.service.userService userService) {
//        WeChatUtils.userService = userService;
//    }
//
//    @Autowired
//    public WeChatUtils(userRelationService userRelationService) {
//        WeChatUtils.userRelationService = userRelationService;
//    }

    @Autowired
    public WeChatUtils(com.support.service.userService userService, userRelationService userRelationService) {
        WeChatUtils.userService = userService;
        WeChatUtils.userRelationService = userRelationService;
    }


    public static String getWXMessage(String code) {
        try {
            log.info("Start getSessionKey");
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx13983661c060e7e9&secret=ecefedfdd9b61b79314fec6762c66fc2&js_code="
                    + code + "&grant_type=authorization_code";
            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//            String body=responseEntity.getBody();
            String body = restTemplate.getForObject(url, String.class);
//            Optional.ofNullable(body).orElseGet(() -> {
//                log.info("网络超时");
//                return "Network Timeout";
//            });
            if (StringUtils.isEmpty(body)) {
                log.info("网络超时");
                return "Network Timeout";
            }
            if (body.contains("errcode")) {
                Object errcode = JSONObject.parseObject(body).get("errcode");
                log.info("微信返回的错误码{}", errcode);
                return "errcode:" + errcode;
            }
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * @return 获取sessionKey
     */
    public static String getSessionKey() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", "wx13983661c060e7e9"); //
        params.put("APPSECRET", "ecefedfdd9b61b79314fec6762c66fc2"); //
        String body = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
//                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}",
//                String.class, params);
//        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        return object.getString("access_token");
    }

    /**
     * @param WeChatMessage
     * @return 微信推送通知
     */
    public static String push(WeChatMessage WeChatMessage) {
        RestTemplate restTemplate = new RestTemplate();
        // 这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="
                + getSessionKey();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, WeChatMessage, String.class);
        return responseEntity.getBody();
    }

    public void tuiSong(Integer id) {
        String time = TimeConversionUtil.getTimeStrNowByInstant();
        Map<String, WeChatTemplateData> data = new HashMap<>();
        WeChatMessage weChatMessage = new WeChatMessage();
        List<userRelation> userRelations = userRelationService.findByConcern(id);
        user u = userService.findById(id).get();
        userRelations.forEach(i -> {
            user user = userService.findById(i.getConcerned()).get();
            data.put("thing1", new WeChatTemplateData("可能摔倒,或者紧急需要帮忙"));
            data.put("date2", new WeChatTemplateData(time));
            data.put("name3", new WeChatTemplateData(u.getName()));
            weChatMessage.setTouser(user.getOpenId());
            weChatMessage.setTemplate_id("ceHT9A7PemdGKavySKZySYHeiAdW8KgSox32Xqh9oOY");
            weChatMessage.setPage("pages/openMap/openMap?longitude=" + u.getLongitude() + "&latitude=" + u.getLatitude());
            weChatMessage.setData(data);
            weChatMessage.setMiniprogram_state("trial");
            String s = WeChatUtils.push(weChatMessage);
            log.info(s);
        });
    }
}
