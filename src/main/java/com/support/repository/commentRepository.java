package com.support.repository;

import com.support.pojo.comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

/**
 * @InterfaceName commentRepository
 * @Author 吴俊淇
 * @Date 2020/3/28 14:33
 * @Version 1.0
 **/
public interface commentRepository extends CrudRepository<comment, Integer> {
    /**
     * 通过动态id获取到评论者的名字头像和评论内容-------------自定义连接查询
     * @param trendsId
     */
    @Query(value = "SELECT u.name,u.photo,c.content FROM user_info u join comment c on u.id=c.user_id where c.trends_id=?1",nativeQuery = true)
    List<Map<String, Object>> findNameAndPhotoAndContentByTrendsId(Integer trendsId);
}
