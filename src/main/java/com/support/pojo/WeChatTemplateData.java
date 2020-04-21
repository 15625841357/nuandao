package com.support.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName TemplateData
 * @Author 吴俊淇
 * @Date 2020/4/7 14:20
 * @Version 1.0
 **/

/**
 *微信模板数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChatTemplateData implements Serializable {
    private Object value;
}
