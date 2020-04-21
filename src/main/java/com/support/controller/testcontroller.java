package com.support.controller;

import com.support.repository.communityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName testcontroller
 * @Author 吴俊淇
 * @Date 2020/3/19 15:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/tasks")
public class testcontroller {
    @Autowired
    private communityRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    public String listTasks(){
        return "跳转完成";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String newTasks(){
        return "创建了一个新的任务";
    }

    @PutMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId")Integer id){
        return "更新了一下id为:"+id+"的任务";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTasks(@PathVariable("taskId")Integer id){
        return "删除了id为:"+id+"的任务";
    }

    @GetMapping("/test")
    public int test(){
        return 1;
    }
}
