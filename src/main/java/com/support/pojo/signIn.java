package com.support.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName dianzan
 * @Author 吴俊淇
 * @Date 2020/3/27 14:24
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "signIn")
public class signIn implements Serializable {
    @Id
    private Integer userId;

    private String text;

    private Integer integral;
}
