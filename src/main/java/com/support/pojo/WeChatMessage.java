package com.support.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName WeChatMessage
 * @Author 吴俊淇
 * @Date 2020/4/7 14:24
 * @Version 1.0
 **/

/**
 * 小程序推送所需数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatMessage implements Serializable {
    private String touser;// 用户openid
    private String template_id;// 订阅消息模版id
    private String page = "index/index";// 默认跳到小程序首页
    private Map<String, WeChatTemplateData> data;// 推送文字
    private String miniprogram_state;// 默认跳到小程序首页
}
