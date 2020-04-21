package com.support.pojo;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName community
 * @Author 吴俊淇
 * @Date 2020/3/19 14:42
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community_info")
public class community implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Expose()
    private Integer id;

    @Expose()
    @Column(name = "name")
    private String name;

    @Expose()
    @Column(name = "email")
    private String email;

    @Expose(serialize = false,deserialize = true)
    @Column(name = "password")
    private String password;

    @Expose()
    @Column(name = "address")
    private String address;

    @Expose(serialize = false,deserialize = true)
    @Column(name = "role")
    private String role;
}
