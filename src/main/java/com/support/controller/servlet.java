package com.support.controller;

import com.support.pojo.comment;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName servlet
 * @Author 吴俊淇
 * @Date 2020/3/19 17:17
 * @Version 1.0
 **/
@WebServlet(name = "firstServlet", urlPatterns = "/firstServlet")
public class servlet extends HttpServlet implements Serializable{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        //        String referer = req.getHeader("Referer");
//        String name = req.getServerName();
//        if(referer !=null && referer.contains(name)){
//            resp.getWriter().append("下载..............");
//            resp.getWriter().flush();
//        }else{
//            req.getRequestDispatcher("/community/index.html").forward(req, resp);
//        }
//        resp.setHeader("Refresh","5;URL=/community/index.html");
//        resp.getWriter().append("5秒后跳转");


        Cookie[] cookies = req.getCookies();
        String lastTime = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("lastAccess")) {
                lastTime = cookie.getValue();
                break;
            }
        }
        if (StringUtils.isEmpty(lastTime))
            resp.getWriter().write("首次登录");
        else
            resp.getWriter().write("非首次访问  --- lastTime:" + lastTime);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss").withZone(ZoneId.systemDefault());//DateTimeFormatter --- 线程安全
        String time = dateFormat.format(Instant.now());  //Instant --- 获取时间戳
        Cookie c = new Cookie("lastAccess", time);
        c.setMaxAge(3600);
        resp.addCookie(c);
        resp.getWriter().flush();
        resp.getWriter().close();
    }
}


