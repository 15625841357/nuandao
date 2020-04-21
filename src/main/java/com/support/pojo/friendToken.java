package com.support.pojo;

import com.support.pojo.key.userKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName friendVerification
 * @Author 吴俊淇
 * @Date 2020/3/24 18:46
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(userKey.class)
@Table(name = "friendToken")
public class friendToken implements Serializable {
    @Id
    private Integer concern;

    @Id
    private Integer concerned;

    private Integer bFollow;

}
