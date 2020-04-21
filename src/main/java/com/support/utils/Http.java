package com.support.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @ClassName Http
 * @Author 吴俊淇
 * @Date 2020/3/23 18:05
 * @Version 1.0
 **/
public class Http {
    public Http(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //默认
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("XDomainRequestAllowed", "1");
        httpServletRequest.setCharacterEncoding("UTF-8");
    }

    public Http(HttpServletResponse httpServletResponse) throws Exception {
        //默认
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("XDomainRequestAllowed", "1");
    }

    public void setEncryption(HttpServletResponse httpServletResponse, String s, long j) {
        httpServletResponse.setHeader("encryption", s + "-" + j);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "encryption");
    }
}
