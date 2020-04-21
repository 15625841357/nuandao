package com.support.interceptor;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.support.utils.Http;
import com.support.utils.JwtTokenUtils;
import com.support.utils.RedisUtil;
import com.support.utils.TimeConversionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @ClassName InterceptorConfig
 * @Author 吴俊淇
 * @Date 2020/3/23 17:11
 * @Version 1.0
 **/


@Component
@Slf4j
//@CrossOrigin(origins = "*", maxAge = 3600)
public class InterceptorConfig implements HandlerInterceptor {
    @Resource
    private RedisUtil redisUtil;
    private Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info("---------------------开始进入请求地址拦截----------------------------");

        new Http(httpServletRequest, httpServletResponse);
        String date = TimeConversionUtil.getTimeStrNowByInstant();
        String ip = httpServletRequest.getRemoteAddr();
        String url = httpServletRequest.getRequestURL().toString();
        String tokenHeader = httpServletRequest.getHeader("encryption");
        if (tokenHeader == null) {
            log.info("{} {} 访问了 {}，但被拦截了，原因是没有登录态 ------false", date, ip, url);
            httpServletResponse.getWriter().append(gson.toJson(ImmutableMap.of("login", "false")));
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
            return false;
        } else {//有tokenHeader
            if (redisUtil.hasKey("encryption:" + tokenHeader)) {//true
                log.info("{} {} 访问了 {}   ------true", date, ip, url);
                return true;
            } else {//false
                log.info("{} {} 访问了 {}，但被拦截了，原因是登录态过期 ------false", date, ip, url);
                httpServletResponse.getWriter().append(gson.toJson(ImmutableMap.of("login", "false")));
                httpServletResponse.getWriter().flush();
                httpServletResponse.getWriter().close();
                return false;
            }
        }
//        Object id = httpServletRequest.getSession().getAttribute("communityId");
//        if (id == null) {
//            log.info("{} {} 访问了 {}，但被拦截了，原因是未登录", date, ip, url);
//            log.info("id :{}", id);
//            httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest, httpServletResponse);
//            return false;
//        } else {
//            log.info("true");
//            log.info("id :{}", id);
//            return true;
//        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}