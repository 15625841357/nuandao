package com.support.pojo;

import com.support.pojo.key.userKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName userRelation
 * @Author 吴俊淇
 * @Date 2020/3/20 23:54
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(userKey.class)
@Table(name = "user_Relation")
public class userRelation implements Serializable {
    @Id
    private Integer concern;

    @Id
    private Integer concerned;

    private String RelationName;


}
