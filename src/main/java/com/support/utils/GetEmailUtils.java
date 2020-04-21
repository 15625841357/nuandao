package com.support.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName GetEmail
 * @Author 吴俊淇
 * @Date 2020/3/27 20:29
 * @Version 1.0
 **/
public class GetEmailUtils {
    public static String GetEmail(HttpServletRequest request){
        String token = request.getHeader(JwtTokenUtils.TOKEN_HEADER).replace(JwtTokenUtils.TOKEN_PREFIX, "");
        return JwtTokenUtils.getUsername(token);
    }
}
