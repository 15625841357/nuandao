package com.support.pojo.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName community_user
 * @Author 吴俊淇
 * @Date 2020/3/26 22:57
 * @Version 1.0
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "community_user")
public class community_user {
    @Id
    @Column(name = "community_id")
    private Integer community_id;

    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "address")
    private String address;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "role")
    private transient String role;

    @Column(name = "openId")
    private transient String openId;

    @Column(name = "secretKey")
    private transient String secretKey;
}
