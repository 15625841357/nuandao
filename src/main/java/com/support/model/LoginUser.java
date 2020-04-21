package com.support.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName LoginUser
 * @Author 吴俊淇
 * @Date 2020/3/19 14:00
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {

    private String email;
    private String password;
    private Integer rememberMe;
}
