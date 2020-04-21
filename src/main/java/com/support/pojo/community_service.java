package com.support.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName community_service
 * @Author 吴俊淇
 * @Date 2020/3/20 14:52
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "community_service")
public class community_service implements Serializable {
    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer community_service_id;

    private Integer community_id;

    private Integer userId;

    private String remarks;//备注

    private String type;

    @ApiModelProperty(hidden = true)
    @CreatedDate
    private Date createTime;

    @ApiModelProperty(value = "status", name = "status", example = "0:用户发布服务，但社区未受理；1:社区同意；2:社区已完成", required = true)
    private Integer status;

}
