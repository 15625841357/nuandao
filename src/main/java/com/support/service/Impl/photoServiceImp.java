package com.support.service.Impl;

import com.support.service.photoService;
import com.support.utils.RedisUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;
import sun.misc.BASE64Encoder;
import sun.net.www.content.image.jpeg;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * @ClassName photoServiceImp
 * @Author 吴俊淇
 * @Date 2020/3/28 23:17
 * @Version 1.0
 **/
@Service
public class photoServiceImp implements photoService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public Map<Object, Object> photo() {

        if (redisUtil.hasKey("photo:photo")) {
            return redisUtil.hmget("photo:photo");
        } else {
            System.out.println("经过static");
            Map<Object, Object> map = new HashMap<>();
            for (int i = 0; i < 9; i++) {
                ClassPathResource classPathResource = new ClassPathResource("static/img/" + (i + 1) + ".jpg");
                //获得文件流，因为在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流
                byte[] data;
                try (InputStream stream = classPathResource.getInputStream()) {
                    data = new byte[stream.available()];
                    stream.read(data);
                    Base64.Encoder encoder = Base64.getEncoder();
                    String photo = "data:image/jpg;base64," + encoder.encodeToString(data).trim();
                    redisUtil.hset("photo:photo", String.valueOf((i+1)), photo);
                    map.put(i + 1, photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return map;
        }
    }
}
