package com.example.demo.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@ApiModel(value = "Others",description = "其他类")
public class Others implements Serializable {
    @ApiModelProperty(value = "姓名",dataType = "String",example = "小妈")
    private String name;
    @ApiModelProperty("学生ID")
    private Integer student_id;
    private String desc;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
