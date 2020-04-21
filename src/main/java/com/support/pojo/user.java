package com.support.pojo;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @ClassName User
 * @Author 吴俊淇
 * @Date 2020/3/19 13:57
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class user implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose()
    private Integer id;

    @Column(name = "name", length = 100)
    @Expose()
    private String name;

    //一个阿拉伯多少字节
    @Column(name = "age", length = 10)
    @Expose()
    private String age;

    @Expose()
    private String address;

    @Column(name = "sex", length = 10)
    @Expose()
    private String sex;

    @Column(name = "phoneNumber", length = 100)
    @Expose()
    private String phoneNumber;

    @Expose()
    private String longitude;

    @Expose()
    private String latitude;

    @Column(name = "role", length = 20)
    @Expose(serialize = false, deserialize = true)
    private String role;

    @Expose(serialize = false, deserialize = true)
    private String openId;

    @Expose(serialize = false, deserialize = true)
    private String secretKey;

    @Expose()
    private String nickName;

    @Expose()
    private String photo;

}