package com.support.service.Impl;

import com.support.pojo.comment;
import com.support.repository.commentRepository;
import com.support.service.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName commentServiceImpl
 * @Author 吴俊淇
 * @Date 2020/3/28 14:33
 * @Version 1.0
 **/
@Service
public class commentServiceImpl implements commentService {
    @Autowired
    private commentRepository commentRepository;

    @Override
    public comment save(comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Map<String, Object>> findNameAndPhotoAndContentByTrendsId(Integer trendsId) {
        return commentRepository.findNameAndPhotoAndContentByTrendsId(trendsId);
    }
}
