package com.support.pojo;

/**
 * @ClassName likeCount
 * @Author 吴俊淇
 * @Date 2020/3/27 17:16
 * @Version 1.0
 **/

import com.support.pojo.key.trendsKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 点赞
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(trendsKey.class)
public class likeCount implements Serializable {

    @Id
    private Integer userId;

    @Id
    private Integer trendsId;//动态id
}
