package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

/**
 * @description 留言信箱
 * @author 吴啸
 * @date 2020-02-26 14:48:04
 */

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_message")
@ApiModel("留言信箱")
public class TbMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    /**
     * id
     */
    @ApiModelProperty("id")
    @Column(name="id")
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @Column(name="username")
    private String username;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @Column(name="mail")
    private String mail;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @Column(name="phone")
    private String phone;

    /**
     * 信息内容
     */
    @ApiModelProperty("信息内容")
    @Column(name="content")
    private String content;



}
