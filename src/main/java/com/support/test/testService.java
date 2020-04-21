package com.support.test;

import com.support.pojo.test;
import com.support.repository.testRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * @ClassName testService
 * @Author 吴俊淇
 * @Date 2020/4/8 22:11
 * @Version 1.0
 **/
@Service
@Slf4j
public class testService {
    @Autowired
    private testRepository testRepository;

//    @Async
    public void save(test test) {
        testRepository.save(test);
    }

    @Async
    public Future<Object> findById(Integer i) {
        log.info("多线程开始注册模拟");
        Object o = testRepository.findById(i);
        log.info("多线程注册成功");
        return new AsyncResult<>(o);
    }

    @Async
    public Object findById1(Integer i) {
        log.info("多线程开始注册模拟");
        Object o = testRepository.findById(i);
        log.info("多线程注册成功");
        return o;
    }

    public Object find(Integer i) {
        return testRepository.findById(i);
    }
}
