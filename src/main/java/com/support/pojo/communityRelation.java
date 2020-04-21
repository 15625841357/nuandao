package com.support.pojo;

import com.support.pojo.key.communityRelationKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName communityRelation
 * @Author 吴俊淇
 * @Date 2020/3/25 18:05
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "communityRelation")
public class communityRelation implements Serializable {
    @Id
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "communityId")
    private Integer communityId;


}
