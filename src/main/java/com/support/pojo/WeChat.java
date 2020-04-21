package com.support.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName WeChat
 * @Author 吴俊淇
 * @Date 2020/3/22 18:38
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeChat  implements Serializable {
    private String session_key;
    private String openid;
}
