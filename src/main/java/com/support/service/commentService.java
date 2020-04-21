package com.support.service;

import com.support.pojo.comment;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName commentService
 * @Author 吴俊淇
 * @Date 2020/3/28 14:33
 * @Version 1.0
 **/
public interface commentService {
    comment save(comment comment);

    /**
     * 通过动态id获取到评论者的名字头像和评论内容-------------自定义连接查询
     * @param trendsId
     */
    List<Map<String, Object>> findNameAndPhotoAndContentByTrendsId(Integer trendsId);
}
