package com.support.pojo;

import com.support.pojo.key.trendsKey;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

/**
 * @ClassName test
 * @Author 吴俊淇
 * @Date 2020/3/27 22:53
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(trendsKey.class)
@Table(name = "trends")
public class trends implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(hidden = true)
    private Integer trendsId;

    @Id
    private Integer userId;//发布动态的本人id

    @Column(name = "content",length = 400)
    private String content;

    @Lob
    private String images;

    @ApiModelProperty(hidden = true)
    @CreatedDate
    private Date date;
}
