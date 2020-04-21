package com.support.pojo;

import com.support.pojo.key.commentKey;
import com.support.pojo.key.trendsKey;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName comment
 * @Author 吴俊淇
 * @Date 2020/3/28 14:09
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(commentKey.class)
@Table(name = "comment")
@ApiModel(value = "用户评论", description = "用户评论实体类")
public class comment implements Serializable {
    @Id
    @Column(name = "userId")
    private Integer userId;

    @Id
    @Column(name = "trendsId")
    private Integer trendsId;//动态id

    @Id
    @ApiModelProperty(hidden = true)
    @CreatedDate
    @Column(name = "time")
    private Date time;

    @ApiModelProperty(value = "content", name = "content", example = "{month:3,day:5}", required = true)
    @Column(name = "content", length = 400)
    private String content;
}
