package com.support.pojo;

import com.support.pojo.key.community_applicationKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

/**
 * @ClassName community_application
 * @Author 吴俊淇
 * @Date 2020/4/3 22:51
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(community_applicationKey.class)
public class community_application implements Serializable {
    @Id
    private Integer userId;

    @Id
    private Integer communityId;

    private Integer bFollow;
}
