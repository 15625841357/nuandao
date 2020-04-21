package com.support.test;

import com.support.utils.iflytekUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.concurrent.Future;

/**
 * @ClassName TaskTest
 * @Author 吴俊淇
 * @Date 2020/4/8 12:40
 * @Version 1.0
 **/
@Component
@Slf4j
public class TaskTest {
    @Async
    public Future<String> register() {
        log.info("多线程开始注册模拟");
        log.info("多线程注册成功");
        FileInputStream getInputStream=null;
        new iflytekUtils(getInputStream);
        return new AsyncResult<>(iflytekUtils.getS());
    }
}
